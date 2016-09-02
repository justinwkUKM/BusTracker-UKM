$(document).ready(function() {
    // Directory to alerts tree
    var alertRef = firebase.database().ref('/alerts');

    alertRef.on('child_added', function(alertData) {
        populate(alertData);
    });

    function populate(alertData) {
        var row = $("<tr id='" + alertData.key + "' style='cursor: pointer'></tr>");
        var creator = $("<td id='creator'>" + alertData.val().creator + "</td>");
        var subject = $("<td id='subject'>" + alertData.val().subject + "</td>");
        var stage = $("<td id='stage'>" + alertData.val().status + "</td>");
        var updated_at = $("<td id='updated_at'>" + alertData.val().updated_at + "</td>");
        var created_at = $("<td id='created_at'>" + alertData.val().created_at + "</td>");

        row.append(creator, subject, stage, updated_at, created_at).prependTo("tbody");
    }
});