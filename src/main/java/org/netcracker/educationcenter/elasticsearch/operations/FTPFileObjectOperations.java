package org.netcracker.educationcenter.elasticsearch.operations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.netcracker.educationcenter.elasticsearch.Connection;
import org.netcracker.educationcenter.elasticsearch.model.FTPFileObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements Elasticsearch Database operations with (on) FTP file objects
 *
 * @author Mikhail Savin
 */
public class FTPFileObjectOperations {
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
    public void insertFTPFileObject(FTPFileObject ftpFileObject) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("ftpFileObjectId", ftpFileObject.getId());
        jsonMap.put("ftpFileObjectServer", ftpFileObject.getServer());
        jsonMap.put("ftpFileObjectText", ftpFileObject.getText());
        jsonMap.put("ftpFileObjectModificationDate", ftpFileObject.getModificationDate());

        IndexRequest indexRequest = new IndexRequest(INDEX)
                .id(ftpFileObject.getId()).source(jsonMap);
        try {
            IndexResponse indexResponse = connection.getRestHighLevelClient()
                    .index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    /**
     * Gets FTP file object by its id
     *
     * @param id searched FTP file object id
     * @return FTP file object as a String
     * @throws IOException if something wrong with get method
     */
    public String getFTPFileObjectById(String id) throws IOException {
        GetRequest getRequest = new GetRequest(INDEX, id);
        GetResponse getResponse;
        try {
            getResponse = connection.getRestHighLevelClient()
                    .get(getRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException | IOException e) {
            LOG.error(e);
            throw e;
        }
        if (getResponse.isExists()) {
            return getResponse.getSourceAsString();
        } else {
            return "Document was not found";
        }
    }

    /**
     * Deletes FTP file object by its id
     *
     * @param id actual FTP file object id
     */
    public void deleteFTPFileObjectById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, id);
        try {
            DeleteResponse deleteResponse = connection.getRestHighLevelClient()
                    .delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            LOG.error(e);
        }
    }

    /**
     * Updated FTP file object by its id with a given reason
     *
     * @param id actual FTP file object id
     * @param ftpFileObject ftpFileObject instance with a new data
     * @param reason reason for update
     */
    public void updateFTPFileObjectById(String id, FTPFileObject ftpFileObject, String reason) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("ftpFileObjectId", ftpFileObject.getId());
        jsonMap.put("ftpFileObjectServer", ftpFileObject.getServer());
        jsonMap.put("ftpFileObjectText", ftpFileObject.getText());
        jsonMap.put("ftpFileObjectModificationDate", ftpFileObject.getModificationDate());
        jsonMap.put("updated", new Date());
        jsonMap.put("reason", reason);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, id)
                .doc(jsonMap).fetchSource(true); // is fetchSource(true) really needed?
        try {
            UpdateResponse updateResponse = connection.getRestHighLevelClient()
                    .update(updateRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            LOG.error(e);
        }
    }
}
