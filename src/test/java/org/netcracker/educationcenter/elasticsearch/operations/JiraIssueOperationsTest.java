package org.netcracker.educationcenter.elasticsearch.operations;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.netcracker.educationcenter.elasticsearch.Connection;
import org.netcracker.educationcenter.elasticsearch.database.model.JiraIssue;
import org.netcracker.educationcenter.elasticsearch.database.operations.JiraIssueOperations;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * JiraIssueOperations testing class
 *
 * @author Mikhail Savin
 * @see JiraIssueOperations
 */
public class JiraIssueOperationsTest {
    /**
     * ES Connection instance
     */
    Connection connection;

    /**
     * jiraIssueOperations instance to perform DB operations on Jira-issues
     */
    JiraIssueOperations jiraIssueOperations;

    /**
     * Creates connection with Elasticsearch before running tests
     */
    @Before
    public void setUpConnection() {
        connection = new Connection();
        connection.makeConnection();
    }

    @Before
    public void setUpJiraIssuesOperations() {
        jiraIssueOperations = new JiraIssueOperations(connection);
    }

    /**
     * Closes the connection with Elasticsearch after tests completion
     *
     * @throws IOException if something wrong with client instance
     */
    @After
    public void closeConnection() throws IOException {
        connection.closeConnection();
    }

    @Test
    public void insertJiraIssue_onlyOne() throws IOException {
        JiraIssue jiraIssue = new JiraIssue("1","Unexpected behavior", "404 Error, Not Found");
        jiraIssueOperations.insertJiraIssue(jiraIssue);
    }

    @Test
    public void deleteJiraIssue_onlyOne() throws IOException {
        JiraIssue jiraIssue = new JiraIssue("1","Unexpected behavior", "404 Error, Not Found");
        jiraIssueOperations.insertJiraIssue(jiraIssue);
        jiraIssueOperations.deleteJiraIssueById("1");
    }

    @Test
    public void getJiraIssueById_correctId() throws IOException {
        JiraIssue jiraIssue = new JiraIssue("1","Unexpected behavior", "404 Error, Not Found");
        jiraIssueOperations.insertJiraIssue(jiraIssue);
        System.out.print(jiraIssueOperations.getJiraIssueById("1"));
    }

    @Test
    public void getJiraIssueById_incorrectId() throws IOException {
        JiraIssue jiraIssue = new JiraIssue("1","Unexpected behavior", "404 Error, Not Found");
        jiraIssueOperations.insertJiraIssue(jiraIssue);
        System.out.print(jiraIssueOperations.getJiraIssueById("2"));
    }
    @Test
    public void updateJiraIssueById_correctId() throws IOException {
        JiraIssue jiraIssue = new JiraIssue("1","Unexpected behavior", "404 Error, Not Found");
        jiraIssueOperations.insertJiraIssue(jiraIssue);
        JiraIssue updatedJiraIssue = new JiraIssue("1","Unexpected behavior",
                "No errors now. Everything is fine");
        jiraIssueOperations.updateJiraIssueById("1", updatedJiraIssue);
    }
}