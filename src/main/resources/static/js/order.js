document.addEventListener("DOMContentLoaded", function () {
    const serviceSelect = document.getElementById("serviceSelect");
    const subservicesContainer = document.getElementById("subservicesContainer");
    const orderForm = document.getElementById("orderForm");
    const message = document.getElementById("message");

    // بارگذاری سرویس‌ها
    fetch("http://localhost:8080/api/service/allservices")
        .then(res => res.json())
        .then(data => {
            console.log("📦 سرویس‌ها:", data);

            data.forEach(service => {
                const option = document.createElement("option");
                option.value = service.name;  // استفاده از نام سرویس به عنوان مقدار
                option.textContent = service.name;
                serviceSelect.appendChild(option);
            });
        })
        .catch(err => {
            console.error("خطا در دریافت سرویس‌ها:", err);
            message.textContent = "❌ بارگذاری سرویس‌ها با خطا مواجه شد.";
            message.style.color = "red";
        });

    // بارگذاری زیرسرویس‌ها وقتی سرویسی انتخاب میشه
    serviceSelect.addEventListener("change", function () {
        const serviceName = serviceSelect.value;
        console.log("🔵 Selected serviceName:", serviceName);

        if (!serviceName) {
            subservicesContainer.innerHTML = "<p>لطفاً یک سرویس انتخاب کنید.</p>";
            return;
        }

        const url = `http://localhost:8080/api/service/subService/${encodeURIComponent(serviceName)}`;
        console.log("📦 Sending request to:", url);

        fetch(url)
            .then(res => {
                if (!res.ok) {
                    throw new Error(`HTTP error! status: ${res.status}`);
                }
                return res.json();
            })
            .then(subservices => {
                console.log("✅ Received subservices:", subservices);
                subservicesContainer.innerHTML = "";

                if (!subservices || subservices.length === 0) {
                    subservicesContainer.innerHTML = "<p>هیچ زیرسرویسی برای این سرویس وجود ندارد.</p>";
                    return;
                }

                subservices.forEach(sub => {
                    const div = document.createElement("div");
                    div.classList.add("subservice-item");

                    const checkbox = document.createElement("input");
                    checkbox.type = "checkbox";
                    checkbox.id = `sub-${sub.id}`;
                    checkbox.value = sub.id;
                    checkbox.name = "subservices";

                    const label = document.createElement("label");
                    label.htmlFor = `sub-${sub.id}`;
                    label.appendChild(document.createTextNode(sub.name));

                    div.appendChild(checkbox);
                    div.appendChild(label);
                    subservicesContainer.appendChild(div);
                });
            })
            .catch(err => {
                console.error("❌ خطا در دریافت زیرسرویس‌ها:", err);
                subservicesContainer.innerHTML = `<p>خطا در بارگذاری زیرسرویس‌ها: ${err.message}</p>`;
            });
    });

    // ارسال سفارش
    orderForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const selectedService = serviceSelect.value;
        const selectedSubservices = Array.from(
            document.querySelectorAll('input[name="subservices"]:checked')
        ).map(cb => cb.value);

        if (!selectedService || selectedSubservices.length === 0) {
            message.textContent = "لطفاً سرویس و حداقل یک زیرسرویس را انتخاب کنید.";
            message.style.color = "red";
            return;
        }

        const order = {
            serviceName: selectedService,
            subserviceIds: selectedSubservices
        };

        console.log("📤 Submitting order:", order);

        fetch("http://localhost:8080/api/order/create", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(order)
        })
            .then(res => {
                if (!res.ok) {
                    return res.text().then(text => { throw new Error(text) });
                }
                return res.json();
            })
            .then(data => {
                message.textContent = "✅ سفارش با موفقیت ثبت شد.";
                message.style.color = "green";
                orderForm.reset();
                subservicesContainer.innerHTML = "";
            })
            .catch(err => {
                console.error("خطا در ثبت سفارش:", err);
                message.textContent = `❌ ثبت سفارش با خطا مواجه شد: ${err.message}`;
                message.style.color = "red";
            });
    });
});