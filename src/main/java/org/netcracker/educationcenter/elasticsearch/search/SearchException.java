package org.netcracker.educationcenter.elasticsearch.search;

/**
 * Exception class for search methods of DocumentSearch
 *
 * @author Mikhail Savin
 */
public class SearchException extends Exception {

    /**
     * Constructor of the exception with Throwable object and message
     *
     * @param message additional info on exception
     * @param e the exception itself
     */
    public SearchException(String message, Throwable e) {
        super(message, e);
    }
}
