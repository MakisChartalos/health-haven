document.addEventListener('DOMContentLoaded', function () {
    const yearSelect = document.getElementById('year');
    const monthSelect = document.getElementById('month');
    const daySelect = document.getElementById('day');
    const hourSelect = document.getElementById('hour');

    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth() + 1;
    const currentDay = currentDate.getDate();
    const currentHour = currentDate.getHours();

    const monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"];

    // Populate year dropdown
    for (let year = currentYear; year <= currentYear + 7; year++) {
        yearSelect.appendChild(new Option(year, year));
    }

    // Populate month dropdown
    monthNames.forEach((month, index) => {
        monthSelect.appendChild(new Option(month, index + 1));
    });

    // Populate hour dropdown
    for (let hour = 9; hour <= 21; hour++) {
        hourSelect.appendChild(new Option(`${hour}:00`, hour));
    }

    // Update the days dropdown based on selected year and month
    function updateDays() {
        const selectedYear = parseInt(yearSelect.value);
        const selectedMonth = parseInt(monthSelect.value);
        daySelect.innerHTML = '<option value="" disabled selected>Select Day</option>';

        if (!isNaN(selectedYear) && !isNaN(selectedMonth)) {
            const daysInMonth = new Date(selectedYear, selectedMonth, 0).getDate();
            for (let day = 1; day <= daysInMonth; day++) {
                const option = new Option(day, day);
                if (selectedYear === currentYear && selectedMonth === currentMonth && day < currentDay) {
                    option.disabled = true;
                    option.style.color = 'grey';
                }
                daySelect.appendChild(option);
            }
        }
    }

    // Update months based on selected year
    function updateMonths() {
        const selectedYear = parseInt(yearSelect.value);
        monthSelect.innerHTML = '<option value="" disabled selected>Select Month</option>';

        monthNames.forEach((month, index) => {
            const option = new Option(month, index + 1);
            if (selectedYear === currentYear && index + 1 < currentMonth) {
                option.disabled = true;
                option.style.color = 'grey';
            }
            monthSelect.appendChild(option);
        });

        updateDays();
    }

    // Update hours based on selected date
    function updateHours() {
        const selectedYear = parseInt(yearSelect.value);
        const selectedMonth = parseInt(monthSelect.value);
        const selectedDay = parseInt(daySelect.value);
        hourSelect.innerHTML = '<option value="" disabled selected>Select Hour</option>';

        for (let hour = 9; hour <= 21; hour++) {
            const option = new Option(`${hour}:00`, hour);
            if (selectedYear === currentYear && selectedMonth === currentMonth && selectedDay === currentDay && hour <= currentHour) {
                option.disabled = true;
                option.style.color = 'grey';
            }
            hourSelect.appendChild(option);
        }
    }

    // Initial population of days if year and month are pre-selected
    if (yearSelect.value && monthSelect.value) {
        updateDays();
    }

    // Event listeners to update months, days, and hours when selections change
    yearSelect.addEventListener('change', updateMonths);
    monthSelect.addEventListener('change', updateDays);
    daySelect.addEventListener('change', updateHours);
});
