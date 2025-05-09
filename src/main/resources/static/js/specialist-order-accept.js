document.addEventListener("DOMContentLoaded", function () {

    loadOrders();


    document.getElementById("orderRequestForm").addEventListener("submit", function (e) {
        e.preventDefault();

        const specialistId = document.getElementById("specialistId").value;
        const orderId = document.getElementById("orderId").value;
        const suggestion = document.getElementById("suggestion").value;


        if (!orderId) {
            showResponse("لطفاً یک سفارش از لیست انتخاب کنید", false);
            return;
        }

        if (!suggestion || isNaN(suggestion)) {
            showResponse("لطفاً یک قیمت پیشنهادی معتبر وارد کنید", false);
            return;
        }

        const requestData = {
            SpecialistAcceptRequest: 5,
            order: Number(orderId),
            SpecialistSuggestion: parseFloat(suggestion)
        };

        // ارسال درخواست به سرور
        fetch("http://localhost:8080/api/requestorder/request", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw new Error(err.message || "خطا در ارتباط با سرور");
                    });
                }
                return response.json();
            })
            .then(data => {
                showResponse("پیشنهاد شما با موفقیت ثبت شد", true);

                loadOrders();

                resetForm();
            })
            .catch(error => {
                console.error("Error:", error);
                showResponse("خطا در ارسال پیشنهاد: " + error.message, false);
            });
    });


    document.getElementById("resetForm").addEventListener("click", function() {
        resetForm();
    });
});


function loadOrders() {
    fetch("http://localhost:8080/api/order/order/list")
        .then(response => {
            if (!response.ok) {
                throw new Error("خطا در دریافت لیست سفارش‌ها");
            }
            return response.json();
        })
        .then(data => {
            const ordersList = document.getElementById("ordersList");
            ordersList.innerHTML = "";

            if (data.length === 0) {
                ordersList.innerHTML = "<li style='text-align: center; padding: 20px;'>در حال حاضر سفارشی بدون متخصص وجود ندارد</li>";
                return;
            }

            data.forEach(order => {
                const li = document.createElement("li");
                li.className = "order-item";
                li.innerHTML = `
                        <div class="order-details">
                            <div>
                                <strong>شناسه سفارش:</strong> ${order.id}
                                <strong>مشتری:</strong> ${order.customerName || 'نامشخص'}
                            </div>
                            <div>
                                <strong>سرویس:</strong> ${order.serviceName || 'نامشخص'}
                                <strong>قیمت پیشنهادی مشتری:</strong> ${order.orderPriceRequest ? order.orderPriceRequest.toLocaleString() + ' تومان' : 'نامشخص'}
                            </div>
                        </div>
                        <div>
                            <strong>توضیحات:</strong> ${order.description || 'بدون توضیحات'}
                        </div>
                        <div class="order-meta">
                            <small>تاریخ ثبت: ${order.createdAt ? new Date(order.createdAt).toLocaleString('fa-IR') : 'نامشخص'}</small>
                        </div>
                    `;

                li.addEventListener("click", () => {
                    selectOrder(order, li);
                });

                ordersList.appendChild(li);
            });
        })
        .catch(error => {
            console.error("خطا در دریافت لیست سفارش‌ها:", error);
            document.getElementById("ordersList").innerHTML = `<li style="text-align: center; padding: 20px; color: red;">خطا در بارگذاری سفارش‌ها: ${error.message}</li>`;
        });
}


function selectOrder(order, element) {
    // حذف کلاس selected از همه آیتم‌ها
    document.querySelectorAll(".order-item").forEach(item => {
        item.classList.remove("selected");
    });


    element.classList.add("selected");


    document.getElementById("orderId").value = order.id;
    document.getElementById("selectedOrderId").value = `سفارش #${order.id} - ${order.serviceName}`;


    const suggestionInput = document.getElementById("suggestion");
    suggestionInput.min = order.orderPriceRequest || 0;
    suggestionInput.placeholder = `حداقل ${order.orderPriceRequest ? order.orderPriceRequest.toLocaleString() + ' تومان' : 'مقداری مشخص نشده'}`;
}


function showResponse(message, isSuccess) {
    const responseDiv = document.getElementById("responseMessage");
    responseDiv.textContent = message;
    responseDiv.className = isSuccess ? "success" : "error";
    responseDiv.style.display = "block";


    setTimeout(() => {
        responseDiv.style.display = "none";
    }, 5000);
}


function resetForm() {
    document.getElementById("orderRequestForm").reset();
    document.getElementById("orderId").value = "";
    document.getElementById("selectedOrderId").value = "هیچ سفارشی انتخاب نشده";


    document.querySelectorAll(".order-item").forEach(item => {
        item.classList.remove("selected");
    });


    document.getElementById("responseMessage").style.display = "none";
}