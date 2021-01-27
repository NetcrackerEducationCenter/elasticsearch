package org.netcracker.educationcenter.elasticsearch.database.model;

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
     * ID is randomly created using UUID
     */
    public FTPFileObject() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Creates a new FTP file object with given server, text and date of modification.
     * ID is randomly created using UUID
     *
     * @param server server name of the FTP file object
     * @param text text of the FTP file object
     * @param modificationDate FTP file object modification date
     */
    public FTPFileObject(String server, String text, LocalDate modificationDate) {
        this.id = UUID.randomUUID().toString();
        this.server = server;
        this.text = text;
        this.modificationDate = modificationDate;
    }

    /**
     * Creates a new FTP file object with given id, server, text and date of modification
     *
     * @param id id of the FTP file object
     * @param server server name of the FTP file object
     * @param text text of the FTP file object
     * @param modificationDate FTP file object modification date
     */
    public FTPFileObject(String id, String server, String text, LocalDate modificationDate) {
      this.id = id;
      this.server = server;
      this.text = text;
      this.modificationDate = modificationDate;
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
