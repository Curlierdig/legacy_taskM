// Main JavaScript for Task Manager
document.addEventListener('DOMContentLoaded', function() {
    // Auto-hide flash messages after 5 seconds
    const flashMessages = document.querySelectorAll('.flash-message');
    flashMessages.forEach(function(message) {
        setTimeout(function() {
            message.style.transition = 'opacity 0.5s';
            message.style.opacity = '0';
            setTimeout(function() {
                message.remove();
            }, 500);
        }, 5000);
    });
    
    // Clear form function
    window.clearForm = function() {
        document.getElementById('taskForm').reset();
        document.getElementById('taskId').value = '';
    };
    
    // Format date for input
    const dateInputs = document.querySelectorAll('input[type="date"]');
    dateInputs.forEach(function(input) {
        if (input.value) {
            const date = new Date(input.value);
            input.value = date.toISOString().split('T')[0];
        }
    });
});
