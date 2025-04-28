document.addEventListener("DOMContentLoaded", function () {
    const serviceDropdown = document.getElementById("service");
    const form = document.getElementById("subserviceForm");
    const message = document.getElementById("message");

    // دریافت لیست سرویس‌ها
    fetch("http://localhost:8080/api/service")
        .then(response => response.json())
        .then(services => {
            services.forEach(service => {
                const option = document.createElement("option");
                option.value = service.id;
                option.textContent = service.name;
                serviceDropdown.appendChild(option);
            });
        })
        .catch(err => {
            console.error("❌ خطا در بارگذاری سرویس‌ها:", err);
            message.textContent = "❌ بارگذاری سرویس‌ها با خطا مواجه شد.";
            message.style.color = "red";
        });

    // ارسال فرم افزودن زیرسرویس
    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const subservice = {
            name: document.getElementById("subserviceName").value.trim(),
            description: document.getElementById("description").value.trim(),
            basePrice: parseFloat(document.getElementById("basePrice").value),
            service: {
                id: serviceDropdown.value,
                name: serviceDropdown.options[serviceDropdown.selectedIndex].textContent
            }
        };

        fetch("http://localhost:8080/api/service/add/subservice", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(subservice)
        })
            .then(res => {
                if (!res.ok) throw new Error("ارسال ناموفق بود");
                return res.json();
            })
            .then(data => {
                message.textContent = "✅ زیرسرویس با موفقیت اضافه شد!";
                message.style.color = "green";
                form.reset();
            })
            .catch(error => {
                console.error("❌ خطا در افزودن زیرسرویس:", error);
                message.textContent = "❌ افزودن زیرسرویس با خطا مواجه شد.";
                message.style.color = "red";
            });
    });
});
