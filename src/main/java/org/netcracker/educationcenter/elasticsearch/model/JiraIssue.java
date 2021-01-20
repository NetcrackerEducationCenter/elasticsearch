package org.netcracker.educationcenter.elasticsearch.model;

public class JiraIssue {
    private String id;

    private String issueTitle;

    private String issueBody;

    public JiraIssue() {

    }

    public JiraIssue(String issueTitle, String issueBody) {
        this.issueTitle = issueTitle;
        this.issueBody = issueBody;
    }

    public JiraIssue(String id, String issueTitle, String issueBody) {
        this.id = id;
        this.issueTitle = issueTitle;
        this.issueBody = issueBody;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(String issueBody) {
        this.issueBody = issueBody;
    }
}
