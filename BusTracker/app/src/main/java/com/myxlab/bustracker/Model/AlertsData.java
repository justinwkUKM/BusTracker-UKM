package com.myxlab.bustracker.Model;

public class AlertsData {

    private String title, issue;

    public AlertsData(String title, String issue) {
        this.title = title;
        this.issue = issue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

}
