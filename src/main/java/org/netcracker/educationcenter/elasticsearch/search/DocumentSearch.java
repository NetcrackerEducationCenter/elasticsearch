package org.netcracker.educationcenter.elasticsearch.search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.netcracker.educationcenter.elasticsearch.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for Elasticsearch Database search
 *
 * @author Mikhail Savin
 */
public abstract class DocumentSearch {
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Current connection instance
     */
    private final Connection connection;

    /**
     * Constructor with given connection to interact with ES DB.
     *
     * @param connection current connection
     */
    public DocumentSearch(Connection connection) {
        this.connection = connection;
    }

    /**
     * Searches all matching objects by index and field name which satisfy the text search
     *
     * @param index the index in which to search for results
     * @param name the field name.
     * @param text the query text (to be analyzed).
     * @return list of JSON-Strings (suitable objects)
     */
    public List<String> search(String index, String name, String text) {
        List<String> searchHitList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(name, text)
                .fuzziness(Fuzziness.AUTO);
                //.prefixLength(1)
                //.maxExpansions(10);
        searchSourceBuilder.query(matchQueryBuilder).sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = connection.getRestHighLevelClient()
                    .search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            for (SearchHit hit : searchHits) {
                searchHitList.add(hit.getSourceAsString());
                System.out.println(hit.getScore());
            }
        } catch (IOException e){
            LOG.error(e);
        }
        return searchHitList;
    }
}
