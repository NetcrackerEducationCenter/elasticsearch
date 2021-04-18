package org.netcracker.educationcenter.elasticsearch.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.netcracker.educationcenter.elasticsearch.connection.Connection;

/**
 * This class implements Elasticsearch search on document models
 *
 * @author Mikhail Savin
 * @see DocumentSearch
 */
public class DocumentModelSearch implements DocumentSearch {

    /**
     * Current connection instance
     */
    private final Connection connection;

    /**
     * JSON object mapper
     */
    private final ObjectMapper mapper;

    /**
     * Creates a new DocumentModelSearch instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public DocumentModelSearch(Connection connection) {
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
}
