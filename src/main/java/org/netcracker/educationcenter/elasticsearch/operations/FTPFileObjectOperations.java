package org.netcracker.educationcenter.elasticsearch.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netcracker.educationcenter.elasticsearch.Connection;
import org.netcracker.educationcenter.elasticsearch.model.FTPFileObject;

import java.io.IOException;

/**
 * This class implements Elasticsearch Database operations with (on) FTP file objects
 *
 * @author Mikhail Savin
 * @see ElasticsearchOperations
 */
public class FTPFileObjectOperations implements ElasticsearchOperations{
    private static final Logger LOG = LogManager.getLogger();

    /**
     * FTP file object model's index
     */
    private static final String INDEX = "ftpfileobjects";

    /**
     * Current connection instance
     */
    private static Connection connection;

    /**
     * Creates a new FTPFileObjectOperations instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public FTPFileObjectOperations(Connection connection) {
        FTPFileObjectOperations.connection = connection;
    }

    /**
     * Inserts given FTP file object into the ES Database
     *
     * @param ftpFileObject FTP file object to insert
     */
    public void insertFTPFileObject(FTPFileObject ftpFileObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(ftpFileObject);
        insert(jsonString, ftpFileObject.getId(), INDEX, connection);
    }

    /**
     * Gets FTP file object by its id
     *
     * @param id searched FTP file object id
     * @return FTP file object as a String
     * @throws IOException if something wrong with get method
     */
    public String getFTPFileObjectById(String id) throws IOException {
        return getById(id, INDEX, connection);
    }

    /**
     * Deletes FTP file object by its id
     *
     * @param id actual FTP file object id
     */
    public void deleteFTPFileObjectById(String id) {
        deleteById(id, INDEX, connection);
    }

    /**
     * Updates FTP file object by its id
     *
     * @param id actual FTP file object id
     * @param ftpFileObject ftpFileObject instance with a new data
     */
    public void updateFTPFileObjectById(String id, FTPFileObject ftpFileObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(ftpFileObject);
        updateById(jsonString, id, INDEX, connection);
    }
}
