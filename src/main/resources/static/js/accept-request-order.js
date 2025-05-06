document.addEventListener('DOMContentLoaded', function() {
    // Elements
    const loadOrdersBtn = document.getElementById('load-orders-btn');
    const customerIdInput = document.getElementById('customer-id');
    const ordersList = document.getElementById('orders-list');
    const specialistsList = document.getElementById('specialists-list');
    const specialistsCard = document.getElementById('specialists-card');
    const loadingOrders = document.getElementById('loading-orders');
    const loadingSpecialists = document.getElementById('loading-specialists');
    const confirmSelectBtn = document.getElementById('confirm-select');
    const specialistDetails = document.getElementById('specialist-details');

    // Variables
    let selectedOrderId = null;
    let selectedSpecialistId = null;
    const specialistModal = new bootstrap.Modal(document.getElementById('specialistModal'));

    // Event Listeners
    loadOrdersBtn.addEventListener('click', loadOrdersHandler);
    confirmSelectBtn.addEventListener('click', confirmSpecialistSelection);

    // Functions
    function loadOrdersHandler() {
        const customerId = customerIdInput.value.trim();

        if (!customerId) {
            showAlert('لطفا شناسه مشتری را وارد کنید', 'danger');
            return;
        }

        loadOrders(customerId);
    }

    function loadOrders(customerId) {
        // Reset UI
        resetOrdersUI();

        // Show loading
        loadingOrders.style.display = 'block';

        fetch(`http://localhost:8080/api/order/list/customer/order/${customerId}`)
            .then(handleResponse)
            .then(orders => {
                if (!orders || orders.length === 0) {
                    ordersList.innerHTML = '<div class="alert alert-info">هیچ سفارشی یافت نشد</div>';
                    return;
                }

                renderOrdersList(orders);
            })
            .catch(error => {
                showError(error);
            })
            .finally(() => {
                loadingOrders.style.display = 'none';
            });
    }

    function resetOrdersUI() {
        ordersList.innerHTML = '';
        specialistsCard.style.display = 'none';
        selectedOrderId = null;
    }

    function handleResponse(response) {
        if (!response.ok) {
            throw new Error('خطا در دریافت داده از سرور');
        }
        return response.json();
    }

    function renderOrdersList(orders) {
        orders.forEach(order => {
            // Validate order data
            if (!order || !order.id) {
                console.warn('Invalid order data:', order);
                return;
            }

            const orderItem = createOrderElement(order);
            ordersList.appendChild(orderItem);
        });
    }

    function createOrderElement(order) {
        const orderItem = document.createElement('div');
        orderItem.className = 'list-group-item order-item';
        orderItem.dataset.orderId = order.id;

        // Prepare order data with fallbacks
        const serviceName = order.serviceName || 'سرویس نامشخص';
        const subService = order.subService || '';
        const customerRequest = order.customerRequestService || 'توضیحاتی ارائه نشده';
        const price = order.orderPriceRequest ? order.orderPriceRequest.toLocaleString() : '0';
        const statusInfo = getStatusInfo(order.orderStatus);

        orderItem.innerHTML = `
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6>${serviceName} - ${subService}</h6>
                    <small class="text-muted">${customerRequest}</small>
                </div>
                <div>
                    <span class="status-badge ${statusInfo.class}">${statusInfo.text}</span>
                </div>
            </div>
            <div class="d-flex justify-content-between mt-2">
                <small class="text-muted">شناسه سفارش: ${order.id}</small>
                <span class="price-badge">${price} تومان</span>
            </div>
        `;

        orderItem.addEventListener('click', () => handleOrderClick(orderItem, order.id));

        return orderItem;
    }

    function getStatusInfo(status) {
        switch(status) {
            case 'AWAITING_ARRIVAL':
                return { class: 'status-awaiting', text: 'در انتظار متخصص' };
            case 'IN_PROGRESS':
                return { class: 'status-in-progress', text: 'در حال انجام' };
            case 'COMPLETED':
                return { class: 'status-completed', text: 'تکمیل شده' };
            default:
                return { class: 'status-awaiting', text: 'وضعیت نامشخص' };
        }
    }

    function handleOrderClick(orderItem, orderId) {
        // Update selected item UI
        document.querySelectorAll('.order-item').forEach(item => {
            item.classList.remove('active');
        });
        orderItem.classList.add('active');

        // Load specialists for this order
        selectedOrderId = orderId;
        loadSpecialists(orderId);
        console.log(orderId)
    }

    function loadSpecialists(orderId) {
        resetSpecialistsUI();
        loadingSpecialists.style.display = 'block';
        specialistsCard.style.display = 'block';

        fetch(`http://localhost:8080/api/requestorder/accept/specialist/list/${orderId}`, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            // credentials: 'include' // اگر از احراز هویت استفاده می‌کنید
        })
            .then(async (response) => {
                const data = await response.json();
                if (!response.ok) {
                    throw new Error(data.message || 'خطا در دریافت داده');
                }
                return data;
            })
            .then(specialists => {
                console.log('Specialists data:', specialists);
                if (!specialists || specialists.length === 0) {
                    specialistsList.innerHTML = `
                <div class="alert alert-warning">
                    هنوز هیچ متخصصی برای این سفارش پیشنهاد نداده است
                </div>
            `;
                    return;
                }
                renderSpecialistsList(specialists);
            })
            .catch(error => {
                console.error('Error:', error);
                specialistsList.innerHTML = `
            <div class="alert alert-danger">
                خطا در دریافت لیست متخصصان: ${error.message}
            </div>
        `;
            })
            .finally(() => {
                loadingSpecialists.style.display = 'none';
            });
    }

    function resetSpecialistsUI() {
        specialistsList.innerHTML = '';
        selectedSpecialistId = null;
    }

    function renderSpecialistsList(specialists) {
        specialists.forEach(specialist => {
            // Validate specialist data
            if (!specialist || !specialist.specialistAcceptRequest || !specialist.specialistAcceptRequest.id) {
                console.warn('Invalid specialist data:', specialist);
                return;
            }

            const specialistItem = document.createElement('div');
            specialistItem.className = 'list-group-item specialist-item';
            specialistItem.dataset.specialistId = specialist.specialistAcceptRequest.id;

            // Extract necessary data with fallbacks
            const firstName = specialist.specialistAcceptRequest.firstname || 'نام';
            const lastName = specialist.specialistAcceptRequest.lastname || 'نام خانوادگی';
            const suggestion = specialist.specialistSuggestion ? specialist.specialistSuggestion.toLocaleString() : '0';
            const rating = specialist.specialistAcceptRequest.rating || 'بدون امتیاز'; // اگر فیلد امتیاز وجود دارد

            specialistItem.innerHTML = `
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6>${firstName} ${lastName}</h6>
                    <div class="d-flex flex-column">
                        <small class="text-muted">پیشنهاد قیمت: ${suggestion} تومان</small>
                        <small class="text-muted">امتیاز: ${rating}</small>
                    </div>
                </div>
                <button class="btn btn-success btn-sm btn-select">انتخاب</button>
            </div>
        `;

            // Add click event to select button
            const selectBtn = specialistItem.querySelector('.btn-select');
            selectBtn.addEventListener('click', (e) => {
                e.stopPropagation();
                showSpecialistConfirmationModal(specialist);
            });

            specialistsList.appendChild(specialistItem);
        });
    }

    function createSpecialistElement(specialist) {
        const specialistItem = document.createElement('div');
        specialistItem.className = 'list-group-item specialist-item';
        specialistItem.dataset.specialistId = specialist.SpecialistAcceptRequest.id;

        // Prepare specialist data with fallbacks
        const firstName = specialist.SpecialistAcceptRequest.firstname || 'نام';
        const lastName = specialist.SpecialistAcceptRequest.lastname || 'نام خانوادگی';
        const suggestion = specialist.SpecialistSuggestion ? specialist.SpecialistSuggestion.toLocaleString() : '0';

        specialistItem.innerHTML = `
            <div>
                <h6>${firstName} ${lastName}</h6>
                <small class="text-muted">پیشنهاد قیمت: ${suggestion} تومان</small>
            </div>
            <button class="btn btn-success btn-sm btn-select">انتخاب</button>
        `;

        // Add click event to select button
        const selectBtn = specialistItem.querySelector('.btn-select');
        selectBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            showSpecialistConfirmationModal(specialist);
        });

        return specialistItem;
    }

    function showSpecialistConfirmationModal(specialist) {
        console.log('Specialist data in modal:', specialist); // برای دیباگ

        // بررسی ساختار جدید داده‌ها
        if (!specialist || !specialist.specialistAcceptRequest || !selectedOrderId) {
            console.error('Missing data:', {
                specialist: specialist,
                specialistAcceptRequest: specialist?.specialistAcceptRequest,
                selectedOrderId: selectedOrderId
            });
            showAlert('اطلاعات متخصص ناقص است', 'danger');
            return;
        }

        selectedSpecialistId = specialist.specialistAcceptRequest.id;

        // Prepare modal content - با ساختار جدید داده‌ها
        const firstName = specialist.specialistAcceptRequest.firstname || 'نام';
        const lastName = specialist.specialistAcceptRequest.lastname || 'نام خانوادگی';
        const suggestion = specialist.specialistSuggestion ? specialist.specialistSuggestion.toLocaleString() : '0';

        specialistDetails.innerHTML = `
        <div class="mb-3">
            <p><strong>متخصص:</strong> ${firstName} ${lastName}</p>
            <p><strong>پیشنهاد قیمت:</strong> ${suggestion} تومان</p>
            <p><strong>شناسه سفارش:</strong> ${selectedOrderId}</p>
        </div>
        <div class="alert alert-warning">
            آیا از انتخاب این متخصص اطمینان دارید؟
        </div>
    `;

        specialistModal.show();
    }

    function confirmSpecialistSelection() {
        if (!selectedOrderId || !selectedSpecialistId) {
            showAlert('خطا در انتخاب متخصص: شناسه سفارش یا متخصص نامعتبر است', 'danger');
            specialistModal.hide();
            return;
        }

        // ساخت پارامترهای URL
        const params = new URLSearchParams();
        params.append('orderId', selectedOrderId);
        params.append('specialistId', selectedSpecialistId);

        fetch(`http://localhost:8080/api/order/accept/specialist/request?${params.toString()}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(async (response) => {
                const data = await response.json();
                console.log('Response:', response.status, data); // برای دیباگ

                if (!response.ok) {
                    throw new Error(data.message || 'خطا در سرور');
                }

                specialistModal.hide();
                showAlert('متخصص با موفقیت انتخاب شد', 'success');

                // رفرش لیست سفارشات
                loadOrders(customerIdInput.value.trim());
            })
            .catch(error => {
                console.error('Error:', error);
                showAlert(`خطا در انتخاب متخصص: ${error.message}`, 'danger');
            });
    }

    function showAlert(message, type) {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type} mt-3`;
        alertDiv.textContent = message;

        // Add to top of container
        document.querySelector('.container').prepend(alertDiv);

        // Remove after 5 seconds
        setTimeout(() => {
            alertDiv.remove();
        }, 5000);
    }

    function showError(error) {
        console.error('Error:', error);
        showAlert(error.message || 'خطایی رخ داده است', 'danger');
    }
});