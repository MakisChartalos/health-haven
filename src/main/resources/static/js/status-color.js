 /**
  * Event listener for DOMContentLoaded to style badges based on appointment status.
  */

 document.addEventListener('DOMContentLoaded', function() {
        var badges = document.querySelectorAll('#appointment-list .badge');
        badges.forEach(function(badge) {
            var status = badge.getAttribute('data-status');
            if (status === 'PENDING') {
                badge.classList.add('badge-pending');
            } else if (status === 'CONFIRMED') {
                badge.classList.add('badge-confirmed');
            } else if (status === 'CANCELLED') {
                badge.classList.add('badge-cancelled');
            }
        });
    });