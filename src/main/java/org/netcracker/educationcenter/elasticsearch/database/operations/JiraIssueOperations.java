package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.netcracker.educationcenter.elasticsearch.Connection;
import org.netcracker.educationcenter.elasticsearch.database.model.JiraIssue;

import java.io.IOException;

/**
 * This class implements Elasticsearch Database operations with (on) Jira-issues
 *
 * @author Mikhail Savin
 * @see ElasticsearchOperations
 */
public class JiraIssueOperations extends ElasticsearchOperations {

    /**
     * Jira-issue model's index
     */
    private static final String INDEX = "jiraissues";

    /**
     * Creates a new JiraIssueOperations instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public JiraIssueOperations(Connection connection) {
        super(connection);
    }

    /**
     * Inserts given Jira-issue into the ES Database
     *
     * @param jiraIssue Jira-issue to insert
     */
    public void insertJiraIssue(JiraIssue jiraIssue) throws IOException {
        String jsonString = getMapper().writeValueAsString(jiraIssue);
        insert(jsonString, jiraIssue.getId(), INDEX);
    }

    /**
     * Gets Jira-issue by its id
     *
     * @param id searched Jira-issue id
     * @return Jira-issue as a String
     */
    public String getJiraIssueById(String id) {
        return getById(id, INDEX);
    }

    /**
     * Deletes Jira-issue by its id
     *
     * @param id actual Jira-issue id
     */
    public void deleteJiraIssueById(String id) {
        deleteById(id, INDEX);
    }

    /**
     * Updates Jira-issue by its id
     *
     * @param id actual Jira-issue id
     * @param jiraIssue jiraIssue instance with a new data
     */
    public void updateJiraIssueById(String id, JiraIssue jiraIssue) throws JsonProcessingException {
        String jsonString = getMapper().writeValueAsString(jiraIssue);
        updateById(jsonString, id, INDEX);
    }
}