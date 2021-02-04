package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.educationcenter.elasticsearch.Connection;

/**
 * This class implements Elasticsearch Database operations with (on) Jira-issues
 *
 * @author Mikhail Savin
 * @see ElasticsearchOperations
 */
public class JiraIssueOperations implements ElasticsearchOperations {
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Current connection instance
     */
    private final Connection connection;

    /**
     * JSON object mapper
     */
    private ObjectMapper mapper;

    /**
     * Creates a new JiraIssueOperations instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public JiraIssueOperations(Connection connection) {
        this.mapper = new ObjectMapper();
        this.connection = connection;
    }

    /**
     * @return current logger instance
     */
    @Override
    public Logger getLogger() {
        return LOG;
    }

    /**
     * @return JSON object mapper
     */
    @Override
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * @return current connection instance
     */
    @Override
    public Connection getConnection() {
        return connection;
    }
}