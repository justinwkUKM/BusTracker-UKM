package com.myxlab.bustracker.Model;

public class AlertsData {

    private String subject, type, message, reporter_id, report_id, createdAt;

    public AlertsData(String title, String issue) {
        this.subject = title;
        this.type = issue;
    }

    public AlertsData(String subject, String type, String message, String reporter_id, String report_id, String createdAt) {
        this.subject = subject;
        this.type = type;
        this.message = message;
        this.reporter_id = reporter_id;
        this.report_id = report_id;
        this.createdAt = createdAt;
    }

    public AlertsData(String subject, String type, String message, String report_id, String reporter_id) {
        this.subject = subject;
        this.type = type;
        this.message = message;
        this.report_id = report_id;
        this.reporter_id = reporter_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(String reporter_id) {
        this.reporter_id = reporter_id;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
