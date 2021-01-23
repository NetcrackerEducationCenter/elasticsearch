package org.netcracker.educationcenter.elasticsearch.operations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.netcracker.educationcenter.elasticsearch.Connection;
import org.netcracker.educationcenter.elasticsearch.model.JiraIssue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements Elasticsearch Database operations with (on) Jira-issues
 *
 * @author Mikhail Savin
 */
public class JiraIssueOperations {
    private static final Logger log = LogManager.getLogger();

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
    public void insertJiraIssue(JiraIssue jiraIssue) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("jiraIssueId", jiraIssue.getId());
        jsonMap.put("jiraIssueTitle", jiraIssue.getIssueTitle());
        jsonMap.put("jiraIssueBody", jiraIssue.getIssueBody());

        IndexRequest indexRequest = new IndexRequest("jiraissues")
                .id(jiraIssue.getId()).source(jsonMap);
        try {
            IndexResponse indexResponse = connection.getRestHighLevelClient()
                    .index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Gets Jira-issue by its id
     *
     * @param id searched Jira-issue id
     * @return Jira-issue as a String
     * @throws IOException if something wrong with get method
     */
    public String getJiraIssueById(String id) throws IOException {
        GetRequest getJiraIssuesRequest = new GetRequest("jiraissues", id);
        GetResponse getResponse;
        try {
            getResponse = connection.getRestHighLevelClient()
                    .get(getJiraIssuesRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException | IOException e) {
            log.error(e);
            throw e;
        }
        if (getResponse.isExists()) {
            return getResponse.getSourceAsString();
        } else {
            return "Document was not found";
        }
    }

    /**
     * Deletes Jira-issue by its id
     *
     * @param id actual Jira-issue id
     */
    public void deleteJiraIssueById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest("jiraissues", id);
        try {
            DeleteResponse deleteResponse = connection.getRestHighLevelClient()
                    .delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            log.error(e);
        }
    }

    /**
     * Updated Jira-issue by its id with a given reason
     *
     * @param id actual Jira-issue id
     * @param jiraIssue jiraIssue instance with a new data
     * @param reason reason for update
     */
    public void updateJiraIssueById(String id, JiraIssue jiraIssue, String reason) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("updated", jiraIssue);
        jsonMap.put("reason", reason);
        UpdateRequest updateRequest = new UpdateRequest("jiraissues", id)
                .doc(jsonMap).fetchSource(true); // is fetchSource(true) really needed?
        try {
            UpdateResponse updateResponse = connection.getRestHighLevelClient()
                    .update(updateRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            log.error(e);
        }
    }
}
