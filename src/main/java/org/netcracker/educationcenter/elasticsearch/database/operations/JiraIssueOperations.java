package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.educationcenter.elasticsearch.Connection;
import org.netcracker.educationcenter.elasticsearch.database.model.JiraIssue;

import java.io.IOException;

/**
 * This class implements Elasticsearch Database operations with (on) Jira-issues
 *
 * @author Mikhail Savin
 * @see ElasticsearchOperations
 */
public class JiraIssueOperations implements ElasticsearchOperations{
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Jira-issue model's index
     */
    private static final String INDEX = "jiraissues";

    /**
     * Current connection instance
     */
    private static Connection connection;

    /**
     * Creates a new JiraIssueOperations instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public JiraIssueOperations(Connection connection) {
        JiraIssueOperations.connection = connection;
    }

    /**
     * Inserts given Jira-issue into the ES Database
     *
     * @param jiraIssue Jira-issue to insert
     */
    public void insertJiraIssue(JiraIssue jiraIssue) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(jiraIssue);
        insert(jsonString, jiraIssue.getId(), INDEX, connection);
    }

    /**
     * Gets Jira-issue by its id
     *
     * @param id searched Jira-issue id
     * @return Jira-issue as a String
     * @throws IOException if something wrong with get method
     */
    public String getJiraIssueById(String id) throws IOException {
        return getById(id, INDEX, connection);
    }

    /**
     * Deletes Jira-issue by its id
     *
     * @param id actual Jira-issue id
     */
    public void deleteJiraIssueById(String id) {
        deleteById(id, INDEX, connection);
    }

    /**
     * Updates Jira-issue by its id
     *
     * @param id actual Jira-issue id
     * @param jiraIssue jiraIssue instance with a new data
     */
    public void updateJiraIssueById(String id, JiraIssue jiraIssue) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(jiraIssue);
        updateById(jsonString, id, INDEX, connection);
    }
}