package org.netcracker.educationcenter.elasticsearch.search;

import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
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
import java.util.Map;

/**
 * Interface for Elasticsearch Database search
 *
 * @author Mikhail Savin
 */
public interface DocumentSearch {

    /**
     * @return current logger instance
     */
    Logger getLogger();

    /**
     * @return current connection instance
     */
    Connection getConnection();

    /**
     * @return index of the sought object
     */
    String getIndex();

    /**
     * Searches all matching objects by index and field name which satisfy the text search
     *
     * @param text the query text (to be analyzed).
     * @param name the field name.
     * @return list of JSON-Strings (suitable objects)
     */
    default List<String> search(String text, String name) {
        List<String> searchHitList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(getIndex());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(name, text)
                .fuzziness(Fuzziness.AUTO);
                //.prefixLength(1)
                //.maxExpansions(10);
        searchSourceBuilder.query(matchQueryBuilder).sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = getConnection().getRestHighLevelClient()
                    .search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            for (SearchHit hit : searchHits) {
                searchHitList.add(hit.getSourceAsString());
            }
        } catch (IOException e){
            getLogger().error(e);
        }
        return searchHitList;
    }

    /**
     * This method implements Elasticsearch multiSearch API.
     * It allows to execute multiple search requests in a single http request in parallel.
     * It should be noted that unlike the search() method, multiSearch() method's signature
     * has only one parameter which is represented as a map of text and field name.
     *
     * @param textAndName map of text to analyze and its corresponding field name.
     * @return list of JSON-Strings (suitable objects). It contains the results of all requests
     */
    default List<String> multiSearch(Map<String, String> textAndName) {
        MultiSearchRequest request = new MultiSearchRequest();

        for (Map.Entry<String, String> entry : textAndName.entrySet()) {
            SearchRequest searchRequest = new SearchRequest();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            QueryBuilder matchQueryBuilder = QueryBuilders
                    .matchQuery(textAndName.get(entry.getKey()), entry.getKey())
                    .fuzziness(Fuzziness.AUTO);
            searchSourceBuilder.query(matchQueryBuilder).sort(new ScoreSortBuilder().order(SortOrder.DESC));
            searchRequest.source(searchSourceBuilder);
            request.add(searchRequest);
        }

        List<String> searchHitList = new ArrayList<>();

        try {
            MultiSearchResponse response = getConnection().getRestHighLevelClient()
                    .msearch(request, RequestOptions.DEFAULT);
            for (int i = 0; i < response.getResponses().length; i++) {
                MultiSearchResponse.Item responseItem = response.getResponses()[i];
                SearchResponse searchResponse = responseItem.getResponse();
                SearchHits searchHits = searchResponse.getHits();

                for (SearchHit hit : searchHits) {
                    searchHitList.add(hit.getSourceAsString());
                }
            }
        } catch (IOException e){
            getLogger().error(e);
        }
        return searchHitList;
    }
}
