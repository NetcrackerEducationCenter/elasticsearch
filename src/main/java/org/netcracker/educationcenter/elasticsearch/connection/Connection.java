package org.netcracker.educationcenter.elasticsearch.connection;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Properties;

/**
 * This class establishes a connection with Elasticsearch Database using rest client
 * to perform requests based on the provided builder.
 *
 * @author Mikhail Savin
 */
public class Connection implements AutoCloseable {

    /**
     * High-level client instance to establish connection
     */
    private RestHighLevelClient restHighLevelClient;

    /**
     * Elasticsearch hostname
     */
    private String hostname;

    /**
     * The name of the scheme.
     */
    private String scheme;

    /**
     * First port number.
     */
    private int port1;

    /**
     * Second port number.
     */
    private int port2;

    /**
     * A constructor which is used to load properties and make a connection with Elasticsearch
     *
     * @param properties connection properties
     */
    public Connection(Properties properties) {
        this.hostname = properties.getProperty(ConnectionConstants.HOSTNAME_PROPERTY_NAME);
        this.scheme = properties.getProperty(ConnectionConstants.SCHEME_PROPERTY_NAME);
        this.port1 = Integer.parseInt(properties.getProperty(ConnectionConstants.PORT1_PROPERTY_NAME));
        this.port2 = Integer.parseInt(properties.getProperty(ConnectionConstants.PORT2_PROPERTY_NAME));
    }

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
                            new HttpHost(hostname, port1, scheme),
                            new HttpHost(hostname, port2, scheme)));
        }
    }

    /**
     * Closes the client to free resources.
     *
     * @throws Exception if something wrong with client instance(for example: it doesn't exist)
     */
    @Override
    public void close() throws Exception {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }
}
