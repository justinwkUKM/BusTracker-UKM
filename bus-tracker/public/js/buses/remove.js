$(document).ready(function () {
    // Display remove bus dialogue on pencil glyphicon pressed.
    $("i.action-remove").click(function() {
        BootstrapDialog.show({
            type: BootstrapDialog.TYPE_DEFAULT,
            title: 'Delete Bus',
            message: 'Do you want to delete ' + $(this).attr('id') + '?',
            buttons: [{
                label: 'Delete',
                cssClass: 'btn-danger',
                hotkey: 13, // Enter.
                action: function() {
                    alert('You pressed Enter.');
                }
            }]
        });
    });
});



