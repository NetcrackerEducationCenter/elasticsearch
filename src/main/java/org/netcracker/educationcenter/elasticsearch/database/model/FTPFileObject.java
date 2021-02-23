package org.netcracker.educationcenter.elasticsearch.database.model;

import org.netcracker.educationcenter.elasticsearch.enums.ModelType;

import java.time.LocalDate;
import java.util.UUID;

/**
 * This class represents FTP file object model in Elasticsearch database
 *
 * @author Mikhail Savin
 */
public class FTPFileObject {

    /**
     * ID of the FTP file object
     */
    private String id;

    /**
     * ID of the request for which this FTPFileObject object was assigned.
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
     * FTP server name
     */
    private String server;

    /**
     * Text of the FTP file object
     */
    private String text;

    /**
     * FTP file object modification date
     */
    private LocalDate modificationDate;

    /**
     * Creates a new FTP file object.
     * FTPFileObject ID is randomly created using UUID
     */
    public FTPFileObject() {
        this.id = UUID.randomUUID().toString();
        this.type = ModelType.FILE;
    }

    /**
     * Creates a new FTP file object with given request ID, source, server, text and date of modification.
     * ID is randomly created using UUID
     *
     * @param requestId ID of the current request
     * @param source source of the FTP file object's data
     * @param server server name of the FTP file object
     * @param text text of the FTP file object
     * @param modificationDate FTP file object modification date
     */
    public FTPFileObject(String requestId, String source, String server, String text, LocalDate modificationDate) {
        this.id = UUID.randomUUID().toString();
        this.requestId = requestId;
        this.source = source;
        this.server = server;
        this.text = text;
        this.modificationDate = modificationDate;
        this.type = ModelType.FILE;
    }

    /**
     * Creates a new FTP file object with given FTPFileObject id, request id, source, server, text and date of modification
     *
     * @param id id of the FTP file object
     * @param requestId id of the current request
     * @param source source of the FTP file object's data
     * @param server server name of the FTP file object
     * @param text text of the FTP file object
     * @param modificationDate FTP file object modification date
     */
    public FTPFileObject(String id, String requestId, String source, String server, String text, LocalDate modificationDate) {
      this.id = id;
      this.requestId = requestId;
      this.source = source;
      this.server = server;
      this.text = text;
      this.modificationDate = modificationDate;
      this.type = ModelType.FILE;
    }

    /**
     * @return FTP file object id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id FTP file object's id to set
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
     * @return FTP file object source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source FTP file object's source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return type of the model (ModelType.FILE by default)
     */
    public ModelType getType() {
        return type;
    }

    /**
     * @param modelType FTP file object's model type to set
     */
    public void setType(ModelType modelType) {
        this.type = modelType;
    }

    /**
     * @return FTP file object server name
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server FTP file object's server name to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return FTP file object text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text FTP file object's text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return FTP file object date of modification
     */
    public LocalDate getModificationDate() {
        return modificationDate;
    }

    /**
     * @param modificationDate FTP file object's date of modification to set
     */
    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }
}
