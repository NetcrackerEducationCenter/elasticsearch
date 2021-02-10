package org.netcracker.educationcenter.elasticsearch.database.operations;

/**
 * Exception class for search methods of DocumentSearch
 *
 * @author Mikhail Savin
 */
public class ElasticsearchOperationsException extends Exception {

    /**
     * Constructor of the exception with Throwable object and message
     *
     * @param message additional info on exception
     * @param e the exception itself
     */
    public ElasticsearchOperationsException(String message, Throwable e) {
        super(message, e);
    }
}
