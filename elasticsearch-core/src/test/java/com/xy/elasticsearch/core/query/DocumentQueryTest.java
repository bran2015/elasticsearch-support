//package com.xy.elasticsearch.core.query;
//
//import org.apache.http.HttpHost;
//import org.apache.http.client.config.RequestConfig;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.MultiMatchQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
///**
// * @author zoubo
// * @version 1.0.0
// * @date 2020/1/17 15:22
// */
//public class DocumentQueryTest {
//
//    private RestHighLevelClient client;
//
//    @BeforeEach
//    void before(){
//        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
//        builder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
//            RequestConfig.Builder custom = RequestConfig.custom()
//                    //超时时间5分钟
//                    .setConnectTimeout(5 * 60 * 1000)
//                    //Socket超时时长
//                    .setSocketTimeout(5 * 60 * 1000).setConnectionRequestTimeout(5 * 60 * 1000);
//            httpAsyncClientBuilder.setDefaultRequestConfig(custom.build());
//            return httpAsyncClientBuilder;
//        });
//        client = new RestHighLevelClient(builder);
//    }
//
//
//    @Test
//    void fieldOne() throws IOException {
//        SearchRequest request = new SearchRequest("demo");
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        //
//        boolQueryBuilder.filter().add(QueryBuilders.termQuery("address.keyword", "广州市"));
//        builder.query(boolQueryBuilder);
//        request.source(builder);
//
//
//
//    }
//
//
//    @Test
//    void multiQuery() throws IOException {
//        SearchRequest request = new SearchRequest("demo");
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        String [] fields = {"name.ik_max_word", "name.english"};
//        boolQueryBuilder.should(QueryBuilders.multiMatchQuery("三四", fields).boost(1.0f)
//                .type(MultiMatchQueryBuilder.Type.MOST_FIELDS));
//        builder.query(boolQueryBuilder);
//        request.source(builder);
//        SearchResponse search = client.search(request);
//        System.out.println(search);
//    }
//
//
//    @Test
//    void multiQueryTwo() throws IOException {
//        SearchRequest request = new SearchRequest("demo");
//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        String [] fields = {"name.ik_max_word", "name.english"};
////        String [] fields = {"name"};
//        boolQueryBuilder.should(QueryBuilders.multiMatchQuery("三四", fields).boost(1.0f)
//                .type(MultiMatchQueryBuilder.Type.MOST_FIELDS));
//        builder.query(boolQueryBuilder);
//        request.source(builder);
//        SearchResponse search = client.search(request);
//        System.out.println(search);
//    }
//
//
//}
