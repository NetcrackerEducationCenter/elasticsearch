package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    /**
     * @return current logger instance
     */
    Logger getLogger();

    /**
     * @return JSON object mapper
     */
    ObjectMapper getMapper();

    /**
     * @return current connection instance
     */
    Connection getConnection();

    /**
     * @return index of the sought object
     */
    String getIndex();

    /**
     * Inserts given object (model) into the ES Database
     *
     * @param object object to insert
     * @param id id of the inserted object
     */
    default void insert(Object object, String id) {
        try {
            String jsonString = getMapper().writeValueAsString(object);
            IndexRequest indexRequest = new IndexRequest(getIndex())
                    .id(id).source(jsonString, XContentType.JSON);
            IndexResponse indexResponse = getConnection().getRestHighLevelClient()
                    .index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            getLogger().error(e);
        }
    }

    /**
     * Gets model's JSON as a String by its id.
     *
     * @param id searched JSON id
     * @return searched JSON as a String
     */
    default String getById(String id) {
        GetRequest getRequest = new GetRequest(getIndex(), id);
        GetResponse getResponse = null;
        try {
            getResponse = getConnection().getRestHighLevelClient()
                    .get(getRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException | IOException e) {
            getLogger().error(e);
        }
        if (getResponse != null && getResponse.isExists()) {
            return getResponse.getSourceAsString();
        } else {
            return "Document was not found";
        }
    }

    /**
     * Deletes object in ES database by its id
     *
     * @param id actual object's id
     */
    default void deleteById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest(getIndex(), id);
        try {
            DeleteResponse deleteResponse = getConnection().getRestHighLevelClient()
                    .delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e){
            getLogger().error(e);
        }
    }

    /**
     * Updates document by its id with given object (with a new data)
     *
     * @param object object (model) to update
     * @param id actual object's (model's) id
     */
    default void updateById(Object object, String id) {
        try {
            String jsonString = getMapper().writeValueAsString(object);
            UpdateRequest updateRequest = new UpdateRequest(getIndex(), id)
                    .doc(jsonString, XContentType.JSON).fetchSource(true); // is fetchSource(true) really needed?
            UpdateResponse updateResponse = getConnection().getRestHighLevelClient()
                    .update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e){
            getLogger().error(e);
        }
    }
}
