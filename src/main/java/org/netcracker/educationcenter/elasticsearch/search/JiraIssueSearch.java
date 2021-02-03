package org.netcracker.educationcenter.elasticsearch.search;

import org.netcracker.educationcenter.elasticsearch.Connection;

import java.util.List;

/**
 * This class implements Elasticsearch search on Jira-issues
 *
 * @author Mikhail Savin
 * @see DocumentSearch
 */
public class JiraIssueSearch extends DocumentSearch {

    /**
     * Jira-issue model's index
     */
    private static final String INDEX = "jiraissues";

    /**
     * Creates a new JiraIssueSearch instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public JiraIssueSearch(Connection connection) {
        super(connection);
    }

    /**
     * This method searches for all matching JiraIssues
     *
     * @param name the field name of JiraIssue (e.g. issueTitle, issueBody)
     * @param text the text of the field to be analyzed
     * @return list of JSON-Strings (suitable objects)
     */
    public List<String> searchForJiraIssues(String name, String text) {
        return search(INDEX, name, text);
    }
}
