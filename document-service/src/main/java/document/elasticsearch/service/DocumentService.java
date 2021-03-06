package document.elasticsearch.service;

import document.domain.Descriptor;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
public class DocumentService {
    private final String elasticsearchIndexName;
    private final String elasticsearchTypeName;
    private final Client elasticSearchClient;

    @Autowired
    public DocumentService(@Value("${elasticsearch.indexName}") String elasticsearchIndexName,
                           @Value("${elasticsearch.typeName}") String elasticsearchTypeName,
                           Client elasticSearchClient) {
        this.elasticsearchIndexName = elasticsearchIndexName;
        this.elasticsearchTypeName = elasticsearchTypeName;
        this.elasticSearchClient = elasticSearchClient;
    }

    public SearchResponse getMaxId() {
        MaxAggregationBuilder aggregation = AggregationBuilders.max("id").field("id");
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).addAggregation(aggregation)
                .execute().actionGet();
    }

    public SearchResponse findOne(long ownerId, long documentId) {
        BoolQueryBuilder boolQuery = boolQuery();
        boolQuery.must(termQuery("ownerId", ownerId));
        boolQuery.must(termQuery("id", documentId));
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(boolQuery).execute()
                .actionGet();
    }

    public SearchResponse findAll(long ownerId) {
        BoolQueryBuilder boolQuery = boolQuery();
        boolQuery.must(termQuery("ownerId", ownerId));
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(boolQuery).execute()
                .actionGet();
    }

    public SearchResponse searchDocumentsForOwner(Long ownerId, String query) {
        System.out.println(query);
        BoolQueryBuilder boolQuery = boolQuery();
        boolQuery.must(termQuery("ownerId", ownerId));
        if (query != null && !query.isEmpty()) {
            boolQuery.should(QueryBuilders.queryStringQuery('*' + query + '*').field("fileName"))
                    .should(QueryBuilders.queryStringQuery('*' + query + '*').field("attachment.content"))
                    .should(QueryBuilders.queryStringQuery('*' + query + '*').field("descriptors.descriptorKey"))
                    .should(QueryBuilders.queryStringQuery('*' + query + '*').field("descriptors.descriptorValue"))
                    .minimumShouldMatch(1);
        }
        System.out.println(boolQuery);
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(boolQuery)
                .execute().actionGet();
    }

    public SearchResponse findByName(Long ownerId, String fileName) {
        System.out.println("fileName " + fileName);
        BoolQueryBuilder boolQuery = boolQuery();
        boolQuery.must(termQuery("ownerId", ownerId));
        boolQuery.must(termQuery("fileName", fileName));
        System.out.println(boolQuery);
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(boolQuery).execute()
                .actionGet();
    }

    public SearchResponse findDocumentsForOwnerByDescriptors(Descriptor descriptor) {
        Map<String, String> propertyValues = new HashMap<>();

        propertyValues.put("descriptors.documentTypeId", descriptor.getDocumentTypeId() + "");
        propertyValues.put("descriptors.descriptorKey", descriptor.getDescriptorKey());
        propertyValues.put("descriptors.descriptorValue", descriptor.getDescriptorValue());

        NestedQueryBuilder nestedQueryBuilder = nestedBoolQuery(propertyValues, "descriptors");
        System.out.println(nestedQueryBuilder.query());
        return elasticSearchClient.prepareSearch(elasticsearchIndexName).setTypes(elasticsearchTypeName).setQuery(nestedQueryBuilder)
                .execute().actionGet();
    }

    private static NestedQueryBuilder nestedBoolQuery(final Map<String, String> propertyValues, final String nestedPath) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Iterator<String> iterator = propertyValues.keySet().iterator();

        while (iterator.hasNext()) {
            String propertyName = iterator.next();
            String propertValue = propertyValues.get(propertyName);
            MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(propertyName, propertValue);
            boolQueryBuilder.must(matchQuery);
        }

        return QueryBuilders.nestedQuery(nestedPath, boolQueryBuilder, ScoreMode.Total);
    }
}
