package com.myxlab.bustracker.Model;

/**
 * The type Alerts data.
 */
public class AlertsData {

    private String subject, type, message, reporter_id, report_id, createdAt;

    /**
     * Instantiates a new Alerts data.
     *
     * @param title the title
     * @param issue the issue
     */
    public AlertsData(String title, String issue) {
        this.subject = title;
        this.type = issue;
    }

    /**
     * Instantiates a new Alerts data.
     *
     * @param subject     the subject
     * @param type        the type
     * @param message     the message
     * @param reporter_id the reporter id
     * @param report_id   the report id
     * @param createdAt   the created at
     */
    public AlertsData(String subject, String type, String message, String reporter_id, String report_id, String createdAt) {
        this.subject = subject;
        this.type = type;
        this.message = message;
        this.reporter_id = reporter_id;
        this.report_id = report_id;
        this.createdAt = createdAt;
    }

    /**
     * Instantiates a new Alerts data.
     *
     * @param subject     the subject
     * @param type        the type
     * @param message     the message
     * @param report_id   the report id
     * @param reporter_id the reporter id
     */
    public AlertsData(String subject, String type, String message, String report_id, String reporter_id) {
        this.subject = subject;
        this.type = type;
        this.message = message;
        this.report_id = report_id;
        this.reporter_id = reporter_id;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets reporter id.
     *
     * @return the reporter id
     */
    public String getReporter_id() {
        return reporter_id;
    }

    /**
     * Sets reporter id.
     *
     * @param reporter_id the reporter id
     */
    public void setReporter_id(String reporter_id) {
        this.reporter_id = reporter_id;
    }

    /**
     * Gets report id.
     *
     * @return the report id
     */
    public String getReport_id() {
        return report_id;
    }

    /**
     * Sets report id.
     *
     * @param report_id the report id
     */
    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

}
