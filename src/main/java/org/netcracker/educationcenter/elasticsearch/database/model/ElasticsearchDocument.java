package org.netcracker.educationcenter.elasticsearch.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.netcracker.educationcenter.elasticsearch.enums.ModelType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * This class represents universal document model in Elasticsearch database
 *
 * @author Mikhail Savin
 */
public class ElasticsearchDocument {

    /**
     * ID of the document
     */
    private String id;

    /**
     * ID of the request for which this object was assigned.
     * With the help of this ID, other services will know which objects are needed.
     */
    private String requestId;

    /**
     * Provided data source
     */
    private String source;

    /**
     * Type of the object (metadata field)
     */
    private ModelType type;

    /**
     * Title of the document
     */
    private String title;

    /**
     * Body of the document
     */
    private String body;

    /**
     * All comments of the document (if the model type is a ticket)
     */
    private List<String> comments;

    /**
     * Document modification date
     */
    private LocalDate modificationDate;

    /**
     * Creates a new empty document.
     * ID is randomly created using UUID
     */
    public ElasticsearchDocument() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Creates a new document with given request id, source, type, title, body, comments and modification date.
     * Document id is randomly created using UUID
     *
     * @param requestId id of the current request
     * @param source source of the document data
     * @param type type of the document object
     * @param title title of the document
     * @param body body of the document
     * @param comments all comments of the document
     * @param modificationDate modification (or creation) date of the document
     */
    public ElasticsearchDocument(String requestId, String source, ModelType type, String title, String body, List<String> comments,
                     LocalDate modificationDate) {
        this.id = UUID.randomUUID().toString();
        this.requestId = requestId;
        this.source = source;
        this.type = type;
        this.title = title;
        this.body = body;
        this.comments = comments;
        this.modificationDate = modificationDate;
    }

    /**
     * Creates a new document with given id, request id, source, type, title, body, comments and modification date.
     *
     * @param id id of the document
     * @param requestId id of the current request
     * @param source source of the document data
     * @param type type of the document object
     * @param title title of the document
     * @param body body of the document
     * @param comments all comments of the document
     * @param modificationDate modification (or creation) date of the document
     */
    public ElasticsearchDocument(String id, String requestId, String source, ModelType type, String title, String body, List<String> comments,
                                 LocalDate modificationDate) {
        this.id = id;
        this.requestId = requestId;
        this.source = source;
        this.type = type;
        this.title = title;
        this.body = body;
        this.comments = comments;
        this.modificationDate = modificationDate;
    }

    /**
     * @return document id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id document id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return current request id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId current request id to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * @return document source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source document source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return type of the document model
     */
    public ModelType getType() {
        return type;
    }

    /**
     * @param modelType document model type to set
     */
    public void setType(ModelType modelType) {
        this.type = modelType;
    }

    /**
     * @return title of the document
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title title of the document to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return body of the document
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body body of the document to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return all comments of the document (as a List)
     */
    public List<String> getComments() {
        return comments;
    }

    /**
     * @param comments comments of the document to set
     */
    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    /**
     * @return document date of modification
     */
    public LocalDate getModificationDate() {
        return modificationDate;
    }

    /**
     * @param modificationDate document date of modification to set
     */
    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }
}
