package org.netcracker.educationcenter.elasticsearch.database.operations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
     * Inserts given object (model) into the ES Database
     *
     * @param object object to insert
     * @param id id of the inserted object
     * @param index index of the inserted document
     */
    default void insert(Object object, String id, String index) throws ElasticsearchOperationsException {
        try {
            String jsonString = getMapper().writeValueAsString(object);
            IndexRequest indexRequest = new IndexRequest(index)
                    .id(id).source(jsonString, XContentType.JSON);
            IndexResponse indexResponse = getConnection().getRestHighLevelClient()
                    .index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ElasticsearchOperationsException("Insert error", e);
        }
    }

    /**
     * Gets Optional of document model's JsonNode (which can be empty) by id.
     *
     * @param id searched JSON id
     * @return Optional object (empty if not found)
     * @param index index of the document
     */
    default Optional<JsonNode> getById(String id, String index) throws ElasticsearchOperationsException {
        GetRequest getRequest = new GetRequest(index, id);
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
     * Deletes document in ES database by its id
     *
     * @param id actual object's id
     * @param index index of the inserted document
     */
    default void deleteById(String id, String index) throws ElasticsearchOperationsException {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
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
     * @param index index of the updated document
     */
    default void updateById(Object object, String id, String index) throws ElasticsearchOperationsException {
        try {
            String jsonString = getMapper().writeValueAsString(object);
            UpdateRequest updateRequest = new UpdateRequest(index, id)
                    .doc(jsonString, XContentType.JSON).fetchSource(true);
            UpdateResponse updateResponse = getConnection().getRestHighLevelClient()
                    .update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e){
            throw new ElasticsearchOperationsException("Can't update document using deleteById() method", e);
        }
    }
}
