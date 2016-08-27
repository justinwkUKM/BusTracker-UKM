$(document).ready(function () {
    // Display create alert dialogue.
    $("button.action-create").click(function () {
        BootstrapDialog.show({
            type: BootstrapDialog.TYPE_DEFAULT,
            title: 'New Report',
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

                    // Store the new report into database.
                    store(report);

                    // CLose the dialog on button pressed.
                    dialogItself.close();
                }
            }]
        });
    });
});

function store(report) {
    // Generate new random key for reports list.
    var newReportInfoKey = firebase.database().ref().child('reports').push().key;

    // Generate new random key for report-messages list.
    var newReportMessageKey = firebase.database().ref().child('report-messages').push().key;

    // Json to store report data.
    var reportInfoData = {
        creator: report.creator,
        subject: report.subject,
        status: report.status,
        updated_at: report.updated_at,
        created_at: report.created_at
    };

    // JSON to store report-message data.
    var reportMessageData = {
        creator: report.creator,
        message: report.message,
        created_at: report.created_at
    };

    console.log(reportInfoData); // reportData
    console.log('Key: ' + newReportInfoKey); // Log unique key in firebase.

    // Write the data to reports and report-creators list.
    var updates = {};
    updates['/reports/' + newReportInfoKey] = reportInfoData;
    updates['/report-messages/' + newReportInfoKey + '/' + newReportMessageKey] = reportMessageData;

    return firebase.database().ref().update(updates);
}



