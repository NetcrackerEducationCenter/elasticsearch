package org.netcracker.educationcenter.elasticsearch.search;

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
     * @param name the field name.
     * @param text the query text (to be analyzed).
     * @return list of JSON-Strings (suitable objects)
     */
    default List<String> search(String name, String text) {
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
                System.out.println(hit.getScore());
            }
        } catch (IOException e){
            getLogger().error(e);
        }
        return searchHitList;
    }
}
