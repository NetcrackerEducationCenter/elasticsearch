package org.netcracker.educationcenter.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class establishes a connection with Elasticsearch Database using rest client
 * to perform requests based on the provided builder.
 *
 * @author Mikhail Savin
 */
public class Connection implements AutoCloseable {
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Properties instance to load properties from connection.properties using method loadProperties()
     */
    private static Properties properties;

    /**
     * High-level client instance to establish connection
     */
    private RestHighLevelClient restHighLevelClient;

    /**
     * Elasticsearch hostname
     */
    private static final String hostname = properties.getProperty("hostname");

    /**
     * The name of the scheme.
     */
    private static final String scheme = properties.getProperty("scheme");

    /**
     * First port number.
     */
    private static final int port1 = Integer.parseInt(properties.getProperty("port1"));

    /**
     * Second port number
     */
    private static final int port2 = Integer.parseInt(properties.getProperty("port2"));

    /**
     * A constructor which is used to load properties and make a connection with Elasticsearch
     */
    public Connection() {
        loadProperties();
    }

    /**
     * @return High-level client instance
     */
    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    /**
     * This method loads properties from connection.properties to initialize property fields such as
     * hostname, scheme, port1 and port2 later
     */
    private void loadProperties() {
        properties = new Properties();

        try (InputStream inputStream = getClass().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error(e);
        }
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
