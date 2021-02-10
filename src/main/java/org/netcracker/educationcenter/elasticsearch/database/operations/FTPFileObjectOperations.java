package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.netcracker.educationcenter.elasticsearch.connection.Connection;
import org.netcracker.educationcenter.elasticsearch.database.DatabaseConstants;

/**
 * This class implements Elasticsearch Database operations with (on) FTP file objects
 *
 * @author Mikhail Savin
 * @see ElasticsearchOperations
 */
public class FTPFileObjectOperations implements ElasticsearchOperations {

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
    public FTPFileObjectOperations(Connection connection) {
        this.mapper = new ObjectMapper();
        this.connection = connection;
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

    /**
     * @return FTP file object model's index
     */
    @Override
    public String getIndex() {
        return DatabaseConstants.FTP_FILE_OBJECTS_INDEX;
    }
}
