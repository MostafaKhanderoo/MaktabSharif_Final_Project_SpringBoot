document.getElementById("registerForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const form = document.getElementById("registerForm");
    const formData = new FormData(form);

    fetch("http://localhost:8080/api/customer/create", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) throw new Error("Register Failed!");
            return response.json();
        })
        .then(data => {
            document.getElementById("message").textContent = `Register Successful`;
            document.getElementById("message").style.color = "green";
            form.reset();
        })
        .catch(error => {
            console.error(error);
            document.getElementById("message").textContent = "Register Failed!";
            document.getElementById("message").style.color = "red";
        });
});
