package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.netcracker.educationcenter.elasticsearch.connection.Connection;

/**
 * This class implements Elasticsearch Database operations with (on) documents (objects of supported sources)
 *
 * @author Mikhail Savin
 * @see ElasticsearchOperations
 */
public class DocumentModelOperations implements ElasticsearchOperations {

    /**
     * Current connection instance
     */
    private final Connection connection;

    /**
     * JSON object mapper
     */
    private final ObjectMapper mapper;

    /**
     * Creates a new DocumentOperations instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public DocumentModelOperations(Connection connection) {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
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
}
