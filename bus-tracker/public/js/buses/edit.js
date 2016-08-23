$(document).ready(function () {
    // Display edit bus dialogue on pencil glyphicon pressed.
    $("i.action-edit").click(function() {
        BootstrapDialog.show({
            type: BootstrapDialog.TYPE_DEFAULT,
            title: 'Edit Bus',
            message: $(
                '<label for="plateNumber">Plate Number</label>' +
                '<input id="plateNumber" type="text" class="form-control" />' +
                '<label for="color">Color</label>' +
                '<input id="color" type="text"class="form-control" />'
            ),
            buttons: [{
                label: 'Update',
                cssClass: 'btn-primary',
                hotkey: 13, // Enter.
                action: function() {
                    alert('You pressed Enter.');
                }
            }]
        });
    });
});



