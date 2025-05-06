document.addEventListener("DOMContentLoaded", () => {
    const serviceSelect = document.getElementById("serviceSelect");
    const subservicesContainer = document.getElementById("subservicesContainer");
    const orderForm = document.getElementById("orderForm");
    const message = document.getElementById("message");
    let priceInput = createPriceInput();

    loadServices();

    serviceSelect.addEventListener("change", handleServiceChange);
    orderForm.addEventListener("submit", handleFormSubmit);

    async function loadServices() {
        try {
            const response = await fetch("http://localhost:8080/api/service/allservices");
            if (!response.ok) throw new Error("خطا در بارگذاری سرویس‌ها");
            const services = await response.json();
            services.forEach(service => {
                const option = new Option(service.name, service.name);
                serviceSelect.appendChild(option);
            });
        } catch (err) {
            showError("❌ بارگذاری سرویس‌ها ناموفق بود: " + err.message);
        }
    }

    async function handleServiceChange() {
        const serviceName = serviceSelect.value;
        if (!serviceName) {
            subservicesContainer.innerHTML = "<p>لطفاً یک سرویس انتخاب کنید.</p>";
            return;
        }

        try {
            const url = `http://localhost:8080/api/service/subService/${encodeURIComponent(serviceName)}`;
            const response = await fetch(url);
            if (!response.ok) throw new Error("خطا در دریافت زیرسرویس‌ها");

            const subservices = await response.json();
            renderSubservices(subservices);
        } catch (err) {
            showError("❌ دریافت زیرسرویس‌ها ناموفق بود: " + err.message);
        }
    }

    function renderSubservices(subservices) {
        subservicesContainer.innerHTML = "";

        if (!subservices.length) {
            subservicesContainer.innerHTML = "<p>زیرسرویسی وجود ندارد.</p>";
            return;
        }

        const title = document.createElement("h4");
        title.textContent = "زیرسرویس‌ها";
        subservicesContainer.appendChild(title);

        subservices.forEach(sub => {
            const div = document.createElement("div");
            div.className = "form-check mb-2";

            const radio = document.createElement("input");
            radio.type = "radio";
            radio.name = "subService";
            radio.value = sub.name;
            radio.id = `sub-${sub.id}`;
            radio.className = "form-check-input";
            radio.dataset.basePrice = sub.basePrice;
            radio.dataset.subServiceId = sub.id;

            const label = document.createElement("label");
            label.className = "form-check-label";
            label.htmlFor = radio.id;
            label.innerHTML = `${sub.name} <span class="text-muted">(قیمت پایه: ${sub.basePrice} تومان)</span>`;

            div.append(radio, label);
            subservicesContainer.appendChild(div);
        });

        subservicesContainer.appendChild(priceInput);
    }

    async function handleFormSubmit(e) {
        e.preventDefault();
        clearMessage();

        try {
            const selectedSub = document.querySelector('input[name="subService"]:checked');
            if (!selectedSub) throw new Error("لطفاً یک زیرسرویس انتخاب کنید");

            const offeredPrice = parseFloat(priceInput.value);
            const basePrice = parseFloat(selectedSub.dataset.basePrice);
            const subServiceId = selectedSub.dataset.subServiceId;

            if (isNaN(offeredPrice)) throw new Error("قیمت پیشنهادی نامعتبر است");
            if (offeredPrice < basePrice) throw new Error(`قیمت باید حداقل ${basePrice} تومان باشد`);

            const orderData = {
                subService: selectedSub.value,
                customerRequestService: localStorage.getItem("customerId") || "6",
                orderPriceRequest: offeredPrice,

            };


            const response = await fetch("http://localhost:8080/api/order/create", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(orderData)
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || "خطا در ثبت سفارش");
            }

            showSuccess("✅ سفارش شما با موفقیت ثبت شد.");
            orderForm.reset();
            subservicesContainer.innerHTML = "";
        } catch (err) {
            showError("❌ " + err.message);
        }
    }

    function createPriceInput() {
        const input = document.createElement("input");
        input.type = "number";
        input.className = "form-control mt-3";
        input.placeholder = "قیمت پیشنهادی (تومان)";
        input.required = true;
        input.id = "orderPrice";
        return input;
    }

    function showSuccess(text) {
        message.textContent = text;
        message.style.color = "green";
        setTimeout(clearMessage, 4000);
    }

    function showError(text) {
        message.textContent = text;
        message.style.color = "red";
        setTimeout(clearMessage, 5000);
    }

    function clearMessage() {
        message.textContent = "";
    }
});
