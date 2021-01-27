package org.netcracker.educationcenter.elasticsearch.database.operations;

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
import org.elasticsearch.common.xcontent.XContentType;
import org.netcracker.educationcenter.elasticsearch.Connection;

import java.io.IOException;

/**
 * Interface for Elasticsearch Database operations (Insert, Get, Delete, Update)
 *
 * @author Mikhail Savin
 */
public interface ElasticsearchOperations {
    Logger LOG = LogManager.getLogger();

    /**
     * Inserts given JSON String (with model) into the ES Database
     *
     * @param jsonString JSON String of the insertable object
     * @param id id of the insertable object
     * @param index index of the insertable model
     * @param connection current connection instance
     */
    default void insert(String jsonString, String id, String index, Connection connection) {
        IndexRequest indexRequest = new IndexRequest(index)
                .id(id).source(jsonString, XContentType.JSON);
        try {
            IndexResponse indexResponse = connection.getRestHighLevelClient()
                    .index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    /**
     * Gets model's JSON as a String by its id and index.
     *
     * @param id searched JSON id
     * @param index searched JSON index
     * @param connection current connection instance
     * @return searched JSON as a String
     * @throws IOException if something wrong with get method
     */
    default String getById(String id, String index, Connection connection) throws IOException {
        GetRequest getJiraIssuesRequest = new GetRequest(index, id);
        GetResponse getResponse;
        try {
            getResponse = connection.getRestHighLevelClient()
                    .get(getJiraIssuesRequest, RequestOptions.DEFAULT);
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
     * Deletes object in ES database by its id and index
     *
     * @param id actual object's JSON id
     * @param index index of the model
     * @param connection current connection instance
     */
    default void deleteById(String id, String index, Connection connection) {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        try {
            DeleteResponse deleteResponse = connection.getRestHighLevelClient()
                    .delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            LOG.error(e);
        }
    }

    /**
     * Updates JSON by its id
     *
     * @param jsonString new JSON string with updated object info
     * @param id actual object's JSON id
     * @param index index of the model
     * @param connection current connection instance
     */
    default void updateById(String jsonString, String id, String index, Connection connection) {
        UpdateRequest updateRequest = new UpdateRequest(index, id)
                .doc(jsonString, XContentType.JSON).fetchSource(true); // is fetchSource(true) really needed?
        try {
            UpdateResponse updateResponse = connection.getRestHighLevelClient()
                    .update(updateRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            LOG.error(e);
        }
    }
}
