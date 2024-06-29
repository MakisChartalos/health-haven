/**
 * Event listener for DOMContentLoaded to show success modal if the booking was successful.
 */

document.addEventListener('DOMContentLoaded', function() {
    var successMessage = document.getElementById('successMessage').value;
    if (successMessage === 'true') {
        $('#successModal').modal('show');
    }

    document.getElementById('closeModalButton').addEventListener('click', function() {
        window.location.href = "/login";
    });
});