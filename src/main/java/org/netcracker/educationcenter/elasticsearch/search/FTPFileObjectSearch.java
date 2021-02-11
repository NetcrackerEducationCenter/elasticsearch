package org.netcracker.educationcenter.elasticsearch.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.netcracker.educationcenter.elasticsearch.connection.Connection;
import org.netcracker.educationcenter.elasticsearch.database.DatabaseConstants;

/**
 * This class implements Elasticsearch search on FTP file objects
 *
 * @author Mikhail Savin
 * @see DocumentSearch
 */
public class FTPFileObjectSearch implements DocumentSearch {

    /**
     * Current connection instance
     */
    private final Connection connection;

    /**
     * JSON object mapper
     */
    private final ObjectMapper mapper;

    /**
     * Creates a new FTPFileObjectSearch instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public FTPFileObjectSearch(Connection connection) {
        this.mapper = new ObjectMapper();
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
     * @return JSON object mapper
     */
    @Override
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * @return FTP file object model's index
     */
    @Override
    public String getIndex() {
        return DatabaseConstants.FTP_FILE_OBJECTS_INDEX;
    }
}
