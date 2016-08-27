$(document).ready(function() {
    // Global report-message reference.
    var reportId = '';

    // Global counter for report-message number.
    var messageQty = 0;

    $("tbody").on('click', 'tr', function() {
        // Id to selected report.
        reportId = $(this).attr('id');

        // Show modal.
        $("div#response-modal").modal();

        // Get report subject and set to modal header text.
        var reportSubject = $(this).find('td#subject').text();
        $('.modal-title').text(reportSubject);
    });

    $("div#response-modal").on("show.bs.modal", function () {
        // Attach to report-message listener on dialog opened.
        listenReportMessage(reportId);
    }).on("hidden.bs.modal", function () {
        // Clear the text input once is updated.
        $('input#message').val('');

        // Detach from report-message listener on dialog closed.
        unlistenReportMessage(reportId);

        // Empty the data in modal-body dialog closed.
        empty();

        // Reset the counter report-message number.
        messageQty = 0;
    });

    $('button.update').click(function() {
        var reportMessage = {
            creator: 'chunhoong',
            message: $('input#message').val(),
            created_at: moment().format('YYYY-MM-DD , h:mm:ss a')
        };

        // Message log.
        console.log(reportMessage);

        // Hide response modal so does not interrupt the report message.
        $('div#response-modal').hide();

        BootstrapDialog.show({
            title: "Confirm update",
            message: "The message will be sent to everyone. Do you want to continue?",
            closable: false,
            buttons: [{
                    label: "Yes",
                    action: function(dialogItself) {
                        // Update to database.
                        update(reportId, reportMessage);

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

    function listenReportMessage(reportId) {
        // Directory to report-messages list.
        var reportMessageRef = firebase.database().ref('/report-messages' + '/' + reportId);

        reportMessageRef.on('child_added', function(reportMessageData) {
           populate(reportMessageData);
        });
    }

    function unlistenReportMessage(reportId) {
        // Directory to report-messages list.
        var reportMessageRef = firebase.database().ref('/report-messages' + '/' + reportId);

        // Detach listener.
        reportMessageRef.off();
    }

    function populate(reportMessageData) {
        var message = '';

        if (messageQty == 0) {
            message = $('<p>' + reportMessageData.val().message + '</p>');
        }
        else {
            message = $('<hr><p>' + reportMessageData.val().message + '</p>');
        }

        messageQty ++;

        message.appendTo('div.messages');

        console.log(reportMessageData.val().message);
    }

    function empty() {
        $('div.messages').empty();
    }

    function update(reportId, reportMessage) {
        // Directory to report-messages list.
        var reportMessageRef = firebase.database().ref('/report-messages' + '/' + reportId);

        var newReportMessageKey = reportMessageRef.push().key;

        // JSON to store report-message data.
        var reportMessageData = {
            creator: reportMessage.creator,
            message: reportMessage.message,
            created_at: reportMessage.created_at
        };

        var updates = {};
        updates['/report-messages/' + reportId + '/' + newReportMessageKey] = reportMessageData;

        return firebase.database().ref().update(updates);
    }
});
