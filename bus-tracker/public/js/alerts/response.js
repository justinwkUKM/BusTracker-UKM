$(document).ready(function() {
    // Global alert-message reference.
    var alertId = '';

    // Global counter for alert-message number.
    var messageQty = 0;

    $("tbody").on('click', 'tr', function() {
        // Id to selected alert.
        alertId = $(this).attr('id');

        // Show modal.
        $("div#response-modal").modal();

        // Get alert subject and set to modal header text.
        var alertSubject = $(this).find('td#subject').text();
        $('.modal-title').text(alertSubject);
    });

    $("div#response-modal").on("show.bs.modal", function () {
        // Attach to alert-message listener on dialog opened.
        listenAlertMessage(alertId);
    }).on("hidden.bs.modal", function () {
        // Clear the text input once is updated.
        $('input#message').val('');

        // Detach from alert-message listener on dialog closed.
        unlistenAlertMessage(alertId);

        // Empty the data in modal-body dialog closed.
        empty();

        // Reset the counter alert-message number.
        messageQty = 0;
    });

    $('button.update').click(function() {
        var alertMessage = {
            creator: 'chunhoong',
            message: $('input#message').val(),
            created_at: moment().format('YYYY-MM-DD , h:mm:ss a')
        };

        // Message log.
        console.log(alertMessage);

        // Hide response modal so does not interrupt the alert message.
        $('div#response-modal').hide();

        BootstrapDialog.show({
            title: "Confirm update",
            message: "The message will be sent to everyone. Do you want to continue?",
            closable: false,
            buttons: [{
                    label: "Yes",
                    action: function(dialogItself) {
                        // Update to database.
                        update(alertId, alertMessage);

                        // Clear the text input once is updated.
                        $('input#message').val('');

                        dialogItself.close();
                    }
            }, {
                label: 'No',
                action: function(dialogItself) {
                    dialogItself.close();
                }
            }],
            onhide: function() {
                // Unhide response modal.
                $('div#response-modal').show();
            }
        });
    });

    function listenAlertMessage(alertId) {
        // Directory to alert-messages list.
        var alertMessageRef = firebase.database().ref('/alert-messages' + '/' + alertId);

        alertMessageRef.on('child_added', function(alertMessageData) {
           populate(alertMessageData);
        });
    }

    function unlistenAlertMessage(alertId) {
        // Directory to alert-messages list.
        var alertMessageRef = firebase.database().ref('/alert-messages' + '/' + alertId);

        // Detach listener.
        alertMessageRef.off();
    }

    function populate(alertMessageData) {
        var message = '';

        if (messageQty == 0) {
            message = $('<p>' + alertMessageData.val().message + '</p>');
        }
        else {
            message = $('<hr><p>' + alertMessageData.val().message + '</p>');
        }

        messageQty ++;

        message.appendTo('div.messages');

        console.log(alertMessageData.val().message);
    }

    function empty() {
        $('div.messages').empty();
    }

    function update(alertId, alertMessage) {
        // Directory to alert-messages list.
        var alertMessageRef = firebase.database().ref('/alert-messages' + '/' + alertId);

        var newAlertMessageKey = alertMessageRef.push().key;

        // JSON to store alert-message data.
        var alertMessageData = {
            creator: alertMessage.creator,
            message: alertMessage.message,
            created_at: alertMessage.created_at
        };

        var updates = {};
        updates['/alert-messages/' + alertId + '/' + newAlertMessageKey] = alertMessageData;

        return firebase.database().ref().update(updates);
    }
});
