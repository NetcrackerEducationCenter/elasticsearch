package org.netcracker.educationcenter.elasticsearch.database.model;

import org.netcracker.educationcenter.elasticsearch.enums.ModelType;

import java.util.List;
import java.util.UUID;

/**
 * This class represents Jira-issues model in Elasticsearch database
 *
 * @author Mikhail Savin
 */
public class JiraIssue {

    /**
     * ID of the Jira-issue
     */
    private String id;

    /**
     * ID of the request for which this JiraIssue object was assigned.
     * With the help of this ID, other services will know which objects are needed.
     */
    private String requestId;

    /**
     * Provided data source
     */
    private String source;

    /**
     * Type of the object (metadata field)
     */
    private ModelType type;

    /**
     * Title of the Jira-issue
     */
    private String issueTitle;

    /**
     * Body of the Jira-issue
     */
    private String issueBody;

    /**
     * All comments of the Jira-issue
     */
    private List<String> comments;

    /**
     * Creates a new Jira-issue (Technically, an empty issue makes no sense in Jira, i.e. it's impossible to create
     * empty issue, but still this constructor might be helpful).
     * ID is randomly created using UUID
     */
    public JiraIssue() {
        this.id = UUID.randomUUID().toString();
        this.type = ModelType.TICKET;
    }

    /**
     * Creates a new Jira-issue with given request id, source, title, issue body and comments.
     * JiraIssue ID is randomly created using UUID
     *
     * @param requestId id of the current request
     * @param source source of the Jira-issue's data
     * @param issueTitle title of the Jira-issue
     * @param issueBody body of the Jira-issue
     * @param comments all comments of the Jira-issue
     */
    public JiraIssue(String requestId, String source, String issueTitle, String issueBody, List<String> comments) {
        this.id = UUID.randomUUID().toString();
        this.requestId = requestId;
        this.source = source;
        this.issueTitle = issueTitle;
        this.issueBody = issueBody;
        this.comments = comments;
        this.type = ModelType.TICKET;
    }

    /**
     * Creates a new Jira-issue with given JiraIssue id, request id, source, title, issue body and comments.
     *
     * @param id id of the Jira-issue
     * @param requestId id of the current request
     * @param source source of the Jira-issue's data
     * @param issueTitle title of the Jira-issue
     * @param issueBody body of the Jira-issue
     * @param comments all comments of the Jira-issue
     */
    public JiraIssue(String id, String requestId, String source, String issueTitle, String issueBody, List<String> comments) {
        this.id = id;
        this.requestId = requestId;
        this.source = source;
        this.issueTitle = issueTitle;
        this.issueBody = issueBody;
        this.comments = comments;
        this.type = ModelType.TICKET;
    }

    /**
     * @return Jira-issue id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id Jira-issue's id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return current request id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId current request id to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * @return Jira-issue's source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source Jira-issue's source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return type of the model (ModelType.TICKET by default)
     */
    public ModelType getType() {
        return type;
    }

    /**
     * @param modelType Jira-issue's model type to set
     */
    public void setType(ModelType modelType) {
        this.type = modelType;
    }

    /**
     * @return title of the Jira-issue
     */
    public String getIssueTitle() {
        return issueTitle;
    }

    /**
     * @param issueTitle title of the Jira-issue to set
     */
    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    /**
     * @return body of the Jira-issue
     */
    public String getIssueBody() {
        return issueBody;
    }

    /**
     * @param issueBody body of the Jira-issue to set
     */
    public void setIssueBody(String issueBody) {
        this.issueBody = issueBody;
    }

    /**
     * @return all comments of the Jira-issue (as a List)
     */
    public List<String> getComments() {
        return comments;
    }

    /**
     * @param comments comments of the Jira-issue to set
     */
    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
