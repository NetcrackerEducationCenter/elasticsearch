package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.netcracker.educationcenter.elasticsearch.Connection;
import org.netcracker.educationcenter.elasticsearch.database.model.FTPFileObject;

import java.io.IOException;

/**
 * This class implements Elasticsearch Database operations with (on) FTP file objects
 *
 * @author Mikhail Savin
 * @see ElasticsearchOperations
 */
public class FTPFileObjectOperations extends ElasticsearchOperations {

    /**
     * FTP file object model's index
     */
    private static final String INDEX = "ftpfileobjects";

    /**
     * Creates a new FTPFileObjectOperations instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public FTPFileObjectOperations(Connection connection) {
        super(connection);
    }

    /**
     * Inserts given FTP file object into the ES Database
     *
     * @param ftpFileObject FTP file object to insert
     */
    public void insertFTPFileObject(FTPFileObject ftpFileObject) throws JsonProcessingException {
        String jsonString =getMapper().writeValueAsString(ftpFileObject);
        insert(jsonString, ftpFileObject.getId(), INDEX);
    }

    /**
     * Gets FTP file object by its id
     *
     * @param id searched FTP file object id
     * @return FTP file object as a String
     */
    public String getFTPFileObjectById(String id) {
        return getById(id, INDEX);
    }

    /**
     * Deletes FTP file object by its id
     *
     * @param id actual FTP file object id
     */
    public void deleteFTPFileObjectById(String id) {
        deleteById(id, INDEX);
    }

    /**
     * Updates FTP file object by its id
     *
     * @param id actual FTP file object id
     * @param ftpFileObject ftpFileObject instance with a new data
     */
    public void updateFTPFileObjectById(String id, FTPFileObject ftpFileObject) throws JsonProcessingException {
        String jsonString = getMapper().writeValueAsString(ftpFileObject);
        updateById(jsonString, id, INDEX);
    }
}
