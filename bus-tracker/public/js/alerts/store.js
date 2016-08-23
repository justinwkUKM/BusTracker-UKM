$(document).ready(function () {
    // Display create alert dialogue.
    $("button.action-create").click(function () {
        BootstrapDialog.show({
            type: BootstrapDialog.TYPE_DEFAULT,
            title: 'New Alert',
            message: $(
                '<label for="subject">Subject</label>' +
                '<input id="subject" type="text" class="form-control" />' +
                '<label for="message">Message</label>' +
                '<textarea id="message" class="form-control" />' +
                '<label for="status">Status</label>' +
                '<select id="status" class="form-control">' +
                    '<option value="opened">Opened</option>' +
                    '<option value="closed">Closed</option>' +
                '</select>'
            ),
            buttons: [{
                label: 'Create',
                cssClass: 'btn-primary',
                action: function(dialogItself) {
                    var alert = {
                        creator: 'Chun Hoong',
                        subject: $('input#subject').val(),
                        message: $('textarea#message').val(),
                        status: $('select#status option:selected').text(),
                        created_at: moment().format('YYYY-MM-DD , h:mm:ss a'),
                        updated_at: moment().format('YYYY-MM-DD , h:mm:ss a')
                    };

                    // Store the new alert into database.
                    store(alert);

                    // CLose the dialog on button pressed.
                    dialogItself.close();
                }
            }]
        });
    });
});

function store(alert) {
    // Generate new random key for alerts list.
    var newAlertInfoKey = firebase.database().ref().child('alerts').push().key;

    // Generate new random key for alert-messages list.
    var newAlertMessageKey = firebase.database().ref().child('alert-messages').push().key;

    // Json to store alert data.
    var alertInfoData = {
        creator: alert.creator,
        subject: alert.subject,
        status: alert.status,
        updated_at: alert.updated_at,
        created_at: alert.created_at
    };

    // JSON to store alert-message data.
    var alertMessageData = {
        creator: alert.creator,
        message: alert.message,
        created_at: alert.created_at
    };

    console.log(alertInfoData); // alertData
    console.log('Key: ' + newAlertInfoKey); // Log unique key in firebase.

    // Write the data to alerts and alert-creators list.
    var updates = {};
    updates['/alerts/' + newAlertInfoKey] = alertInfoData;
    updates['/alert-messages/' + newAlertInfoKey + '/' + newAlertMessageKey] = alertMessageData;

    return firebase.database().ref().update(updates);
}



