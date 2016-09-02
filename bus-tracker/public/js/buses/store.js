$(document).ready(function () {
    // Url to rest api.
    var url = 'api/buses';

    $("form").on("submit", function(event) {
        // Suppress the default behaviour of html form.
        event.preventDefault();

        var formData = {
            plateNumber: $('#plateNumber').val(),
            color: $('#color').val()
        };

        // JQuery post function shorthand
        $.ajax({
            type: 'POST',
            url: url,
            data: formData,
            dataType: 'json',
            success: function (data) {
                console.log('Success:', data);
                // Dismiss the modal.
                $('#createBusModal').modal('toggle');

                // Reset the value of textfield.
                $('#plateNumber').val('');
                $('#color').val('');
            },
            error: function (data) {
                console.log('Error:', data);

                // Get the snackbar DIV
                var x = $('div#snackBar');

                // Set the message
                x.text('Bus is not added');

                // Add the "show" class to DIV
                x.className = "show";

                // After 3 seconds, remove the show class from DIV
                setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
            }
        });
    });
});
