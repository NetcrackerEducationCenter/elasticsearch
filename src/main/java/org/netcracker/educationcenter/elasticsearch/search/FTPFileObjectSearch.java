package org.netcracker.educationcenter.elasticsearch.search;

import org.netcracker.educationcenter.elasticsearch.Connection;

import java.util.List;

/**
 * This class implements Elasticsearch search on FTP file objects
 *
 * @author Mikhail Savin
 * @see DocumentSearch
 */
public class FTPFileObjectSearch extends DocumentSearch{

    /**
     * FTP file object model's index
     */
    private static final String INDEX = "ftpfileobjects";

    /**
     * Creates a new FTPFileObjectSearch instance with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public FTPFileObjectSearch(Connection connection) {
        super(connection);
    }

    /**
     * This method searches for all matching FTP file objects
     *
     * @param name the field name of FTPFileObject (e.g. text, modificationDate)
     * @param text the text of the field to be analyzed
     * @return list of JSON-Strings (suitable objects)
     */
    public List<String> searchForFTPFileObjects(String name, String text) {
        return search(INDEX, name, text);
    }
}
