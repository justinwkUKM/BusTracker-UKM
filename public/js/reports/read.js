$(document).ready(function() {
    // Directory to reports tree
    var reportRef = firebase.database().ref('/reports');

    reportRef.on('child_added', function(reportData) {
        populate(reportData);
    });

    function populate(reportData) {
        var row = $("<tr id='" + reportData.key + "' style='cursor: pointer'></tr>");
        var creator = $("<td id='creator'>" + reportData.val().creator + "</td>");
        var subject = $("<td id='subject'>" + reportData.val().subject + "</td>");
        var stage = $("<td id='stage'>" + reportData.val().status + "</td>");
        var updated_at = $("<td id='updated_at'>" + reportData.val().updated_at + "</td>");
        var created_at = $("<td id='created_at'>" + reportData.val().created_at + "</td>");

        row.append(creator, subject, stage, updated_at, created_at).prependTo("tbody");
    }
});