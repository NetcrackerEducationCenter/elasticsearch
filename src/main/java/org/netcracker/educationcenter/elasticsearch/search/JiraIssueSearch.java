package org.netcracker.educationcenter.elasticsearch.search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.educationcenter.elasticsearch.connection.Connection;
import org.netcracker.educationcenter.elasticsearch.database.DatabaseConstants;

/**
 * This class implements Elasticsearch search on Jira-issues
 *
 * @author Mikhail Savin
 * @see DocumentSearch
 */
public class JiraIssueSearch implements DocumentSearch {
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Current connection instance
     */
    private final Connection connection;

    /**
     * Creates a new JiraIssueSearch instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public JiraIssueSearch(Connection connection) {
        this.connection = connection;
    }

    /**
     * @return current connection instance
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * @return Jira-issue model's index
     */
    @Override
    public String getIndex() {
        return DatabaseConstants.JIRA_ISSUES_INDEX;
    }
}
