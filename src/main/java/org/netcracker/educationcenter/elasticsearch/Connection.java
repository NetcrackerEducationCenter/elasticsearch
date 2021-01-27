package org.netcracker.educationcenter.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.FileInputStream;
import java.io.IOException;
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
     * Second port number
     */
    private int port2;

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
     * This method loads properties from connection.properties and initializes property fields such as
     * hostname, scheme, port1 and port2
     */
    private void loadProperties() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/resources/connection.properties"));
            hostname = properties.getProperty("hostname");
            scheme = properties.getProperty("scheme");
            port1 = Integer.parseInt(properties.getProperty("port1"));
            port2 = Integer.parseInt(properties.getProperty("port2"));
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
