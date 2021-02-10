package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.netcracker.educationcenter.elasticsearch.connection.Connection;

import java.io.IOException;
import java.util.Optional;

/**
 * Interface for Elasticsearch Database operations (Insert, Get, Delete, Update)
 *
 * @author Mikhail Savin
 */
public interface ElasticsearchOperations {

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
    default void insert(Object object, String id) throws ElasticsearchOperationsException {
        try {
            String jsonString = getMapper().writeValueAsString(object);
            IndexRequest indexRequest = new IndexRequest(getIndex())
                    .id(id).source(jsonString, XContentType.JSON);
            IndexResponse indexResponse = getConnection().getRestHighLevelClient()
                    .index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ElasticsearchOperationsException("Insert error", e);
        }
    }

    /**
     * Gets model's JSON as a String by its id.
     *
     * @param id searched JSON id
     * @return searched JSON as a String
     */
    default Optional<JsonNode> getById(String id) throws ElasticsearchOperationsException {
        GetRequest getRequest = new GetRequest(getIndex(), id);
        GetResponse getResponse;
        try {
            getResponse = getConnection().getRestHighLevelClient()
                    .get(getRequest, RequestOptions.DEFAULT);
            if (getResponse != null && getResponse.isExists()) {
                return Optional.of(getMapper().readTree(getResponse.getSourceAsString()));
            } else {
                return Optional.empty();
            }
        } catch (ElasticsearchException | IOException e) {
            throw new ElasticsearchOperationsException("Can't get by id using getById() method", e);
        }
    }

    /**
     * Deletes object in ES database by its id
     *
     * @param id actual object's id
     */
    default void deleteById(String id) throws ElasticsearchOperationsException {
        DeleteRequest deleteRequest = new DeleteRequest(getIndex(), id);
        try {
            DeleteResponse deleteResponse = getConnection().getRestHighLevelClient()
                    .delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e){
            throw new ElasticsearchOperationsException("Error with deletion using deleteById() method", e);
        }
    }

    /**
     * Updates document by its id with given object (with a new data)
     *
     * @param object object (model) to update
     * @param id actual object's (model's) id
     */
    default void updateById(Object object, String id) throws ElasticsearchOperationsException {
        try {
            String jsonString = getMapper().writeValueAsString(object);
            UpdateRequest updateRequest = new UpdateRequest(getIndex(), id)
                    .doc(jsonString, XContentType.JSON).fetchSource(true);
            UpdateResponse updateResponse = getConnection().getRestHighLevelClient()
                    .update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e){
            throw new ElasticsearchOperationsException("Can't update document using deleteById() method", e);
        }
    }
}
