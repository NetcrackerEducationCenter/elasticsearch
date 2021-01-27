package org.netcracker.educationcenter.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * This class establishes a connection with Elasticsearch Database using rest client
 * to perform requests based on the provided builder.
 *
 * @author Mikhail Savin
 */
public class Connection {

    /**
     * High-level client instance to establish connection
     */
    private RestHighLevelClient restHighLevelClient;

    /**
     * @return High-level client instance
     */
    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    /**
     * High-level client internally creates the low-level client
     * (which maintains a pool of connections and starts some threads) to perform requests.
     * Note that we need both ports to be added via builder.
     */
    public synchronized void makeConnection() {
        if (restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost("142.93.122.167", 9200, "http"),
                            new HttpHost("142.93.122.167", 9201, "http")));
        }
    }

    /**
     * Closes the client to free resources.
     * @throws IOException if something wrong with client instance(for example: it doesn't exist)
     */
    public synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }
}
