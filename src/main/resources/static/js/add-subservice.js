document.addEventListener("DOMContentLoaded", function () {
    const serviceDropdown = document.getElementById("service");
    const form = document.getElementById("subserviceForm");
    const message = document.getElementById("message");

    // Load services from backend
    fetch("http://localhost:8080/api/service/allservices")
        .then(response => response.json())
        .then(services => {
            services.forEach(service => {
                try {
                    const parsedName = { title: service.name }; // بدون JSON.parse

                    const option = document.createElement("option");
                    option.value = JSON.stringify({ id: service.id, title: parsedName.title }); // store full info
                    option.textContent = parsedName.title;
                    serviceDropdown.appendChild(option);
                } catch (err) {
                    console.error("Error parsing service name:", err);
                }
            });
        })
        .catch(err => {
            console.error("Error loading services:", err);
            message.textContent = "❌ بارگذاری سرویس‌ها با خطا مواجه شد.";
            message.style.color = "red";
        });

    // Handle subservice form submit
    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const selectedService = JSON.parse(serviceDropdown.value);
        const subserviceName = document.getElementById("subserviceName").value.trim();
        const Description = document.getElementById("description").value.trim();
        const BasePrice = parseFloat(document.getElementById("basePrice").value);

        const subservice = {
            name: subserviceName,
            description: Description,
            basePrice: BasePrice,
            service: {
                id: selectedService.id,
                name: selectedService.title
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
                if (!res.ok) throw new Error("Request failed");
                return res.json();
            })
            .then(data => {
                message.textContent = "✅ زیرسرویس با موفقیت اضافه شد!";
                message.style.color = "green";
                form.reset();
            })
            .catch(error => {
                console.error("Error adding subservice:", error);
                message.textContent = "❌ افزودن زیرسرویس با خطا مواجه شد.";
                message.style.color = "red";
            });
    });
});
