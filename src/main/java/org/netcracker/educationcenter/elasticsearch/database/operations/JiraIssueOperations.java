package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.educationcenter.elasticsearch.Connection;
import org.netcracker.educationcenter.elasticsearch.database.model.JiraIssue;

/**
 * This class implements Elasticsearch Database operations with (on) Jira-issues
 *
 * @author Mikhail Savin
 * @see ElasticsearchOperations
 */
public class JiraIssueOperations extends ElasticsearchOperations {
    private static final Logger LOG = LogManager.getLogger();

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
    public void insertJiraIssue(JiraIssue jiraIssue) {
        try {
            String jsonString = getMapper().writeValueAsString(jiraIssue);
            insert(jsonString, jiraIssue.getId(), INDEX);
        } catch (JsonProcessingException e) {
            LOG.error(e);
        }

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
    public void updateJiraIssueById(String id, JiraIssue jiraIssue) {
        try {
            String jsonString = getMapper().writeValueAsString(jiraIssue);
            updateById(jsonString, id, INDEX);
        } catch (JsonProcessingException e) {
            LOG.error(e);
        }
    }
}