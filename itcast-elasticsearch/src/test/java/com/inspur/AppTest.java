//package com.inspur;
//
//import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
//import org.elasticsearch.action.bulk.BulkRequestBuilder;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.get.MultiGetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.reindex.BulkByScrollResponse;
//import org.elasticsearch.index.reindex.DeleteByQueryAction;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.aggregations.AggregationBuilder;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.bucket.filter.Filter;
//import org.elasticsearch.search.aggregations.bucket.filter.Filters;
//import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
//import org.elasticsearch.search.aggregations.bucket.missing.Missing;
//import org.elasticsearch.search.aggregations.bucket.range.Range;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.aggregations.metrics.avg.Avg;
//import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
//import org.elasticsearch.search.aggregations.metrics.max.Max;
//import org.elasticsearch.search.aggregations.metrics.min.Min;
//import org.elasticsearch.search.aggregations.metrics.sum.Sum;
//import org.elasticsearch.search.sort.SortOrder;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//
//import static junit.framework.TestCase.assertTrue;
//
///**
// * Unit test for simple App.
// * 这里测试了集群的添加文档 和 查询文档的功能,其他都在单节点的上测试, 因为CPU 和 内存受不了
// */
//public class AppTest {
//    /**
//     * Rigorous Test :-)
//     */
//    @Test
//    public void shouldAnswerWithTrue() {
//        assertTrue(true);
//    }
//
//    /**
//     * 测试ES查询 Get
//     */
//    @Test
//    public void testESGet() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
////            GetResponse response = client.prepareGet("index1", "blog", "1").get();
//
//            //数据的查询
//            GetResponse response = client.prepareGet("lib3", "user", "1").get();
//
//            //获取到查询的数据
//            System.out.println(response.getSourceAsString());
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 参加自己的文档数据 测试 add
//     */
//    @Test
//    public void testESAdd() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
////            "{" +
////                "\"id\":\"1\"," +
////                "\"title\":\"Java设计模式之装饰模式\"," +
////                "\"content\":\"在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。\"," +
////                "\"postdate\":\"2018-05-20 14:38:00\"," +
////                "\"url\":\"csdn.net/79239072\"" +
////            "}"
//            //创建数据
//            XContentBuilder doc1 = XContentFactory.jsonBuilder()
//                    .startObject()
//                    .field("id", "1")
//                    .field("title", "Java设计模式之单例模式")
//                    .field("content", "枚举单例模式可以防反射攻击。")
//                    .field("postdate", "2018-02-03")
//                    .field("url", "csdn.net/79247746")
//                    .endObject();
//            //添加文档
//            IndexResponse response = client.prepareIndex("index1", "blog", "1").setSource(doc1).get();
//
//            //如果返回的值为: CREATED 表示创建成功了
//            System.out.println("返回创建状态:" + response.status());
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 测试 delete
//     */
//    @Test
//    public void testESDelete() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            //删除文档
//            DeleteResponse response = client.prepareDelete("index1", "blog", "1").get();
//            //如果返回的值为:  OK 表示删除成功了
//            System.out.println("返回删除状态:" + response.status());
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 测试Update
//     */
//    @Test
//    public void testESUpdate() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            //修改文档
//            UpdateRequest request = new UpdateRequest();
//            request.index("index1")
//                    .type("blog")
//                    .id("8")
//                    .doc(
//                            XContentFactory.jsonBuilder()
//                                    .startObject()
//                                    .field("id", "8")
//                                    .endObject()
//                    );
//
//            UpdateResponse response = client.update(request).get();
//
//            //如果返回的值为:  OK 表示修改成功了
//            System.out.println("返回修改状态:" + response.status());
//            //关闭client端
//            client.close();
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * upsert 测试upsert 功能: 没有只添加不修改, 有就只进行修改
//     */
//    @Test
//    public void testUpsert() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            //创建数据
//            XContentBuilder builder = XContentFactory.jsonBuilder()
//                    .startObject()
//                    .field("id", "2")
//                    .field("title", "Java设计模式之工厂模式")
//                    .field("content", "工厂模式是spring中常见的设计模式。")
//                    .field("postdate", "2019-12-03")
//                    .field("url", "csdn.net/79247746")
//                    .endObject();
//
//            //添加或修改操作文档
//            IndexRequest request = new IndexRequest("index1", "blog", "8").source(builder);
//            UpdateRequest updateRequest = new UpdateRequest("index1", "blog", "8")
//                    .doc(
//                            XContentFactory.jsonBuilder()
//                                    .startObject()
//                                    .field("title", "Java设计模式之搞笑996模式")
//                                    .endObject()
//                    ).upsert(request);
//
//            UpdateResponse response = client.update(updateRequest).get();
//            //如果返回的值为: CREATED 表示创建成功了 OK,表示修改成功了
//            System.out.println("返回状态:" + response.status());
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * MGet 测试查询多个文档的东西
//     */
//    @Test
//    public void testMGet() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            MultiGetResponse responses = client.prepareMultiGet()
//                    .add("index1", "blog", "1", "8")
//                    .add("lib3", "user", "1", "2", "3", "4")
//                    .get();
//
//            responses.forEach(response -> {
//                System.out.println(response.getResponse().getSourceAsString());
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * bulk 批量操作
//     */
//    @Test
//    public void testBulk() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//
//            //1.首先实现批量添加的操作!
//            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
//            bulkRequestBuilder.add(client.prepareIndex("index1", "blog", "2").setSource(
//                    XContentFactory.jsonBuilder()
//                            .startObject()
//                            .field("id", "2")
//                            .field("title", "Java设计模式之工厂模式2222")
//                            .field("content", "工厂模式是spring中常见的设计模式2222。")
//                            .field("postdate", "2019-12-22")
//                            .field("url", "csdn.net/79247746")
//                            .endObject()
//            ));
//            bulkRequestBuilder.add(client.prepareIndex("index1", "blog", "3").setSource(
//                    XContentFactory.jsonBuilder()
//                            .startObject()
//                            .field("id", "3")
//                            .field("title", "Java设计模式之工厂模式3333")
//                            .field("content", "工厂模式是spring中常见的设计模式3333。")
//                            .field("postdate", "2019-12-23")
//                            .field("url", "csdn.net/79247746")
//                            .endObject()
//            ));
//            bulkRequestBuilder.add(client.prepareIndex("index1", "blog", "4").setSource(
//                    XContentFactory.jsonBuilder()
//                            .startObject()
//                            .field("id", "4")
//                            .field("title", "Java设计模式之工厂模式4444")
//                            .field("content", "工厂模式是spring中常见的设计模式4444。")
//                            .field("postdate", "2019-12-24")
//                            .field("url", "csdn.net/79247746")
//                            .endObject()
//            ));
//
//            BulkResponse responses = bulkRequestBuilder.get();
//            System.out.println("bulk hasFailures ------------->:" + responses.hasFailures());
//            responses.forEach(response -> {
//                System.out.println("bulk response status ----------->:" + response.getResponse().status());
//            });
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 查询删除
//     * 查询出满足需求的数据并进行删除!
//     */
//    @Test
//    public void testQueryAndDelete() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            BulkByScrollResponse response = DeleteByQueryAction.INSTANCE
//                    .newRequestBuilder(client)
//                    .filter(QueryBuilders.matchQuery("title", "工厂"))
//                    .source("index1").get();
//
//            System.out.println("删除的个数 ------------------->:" + response.getDeleted());
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * match_all 查询所有
//     * 查询所有
//     */
//    @Test
//    public void testMatchAll() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
//            SearchResponse response = client.prepareSearch("index1")
//                    .setQuery(queryBuilder)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index ------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ----------->:" + key + ", value ----------->:" + value);
//                });
//                System.out.println("======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * match query的测试
//     */
//    @Test
//    public void testMatchQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            QueryBuilder queryBuilder = QueryBuilders.matchQuery("content", "模式");
//            SearchResponse response = client.prepareSearch("index1")
//                    .setQuery(queryBuilder)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index ------------------------------------------------------>:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ---------->:" + key + ", value ---------->:" + value);
//                });
//                System.out.println("=====================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 测试MultiMatchQuery
//     */
//    @Test
//    public void testMultiMatchQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("weiqi", "interests", "name");
//            SearchResponse response = client.prepareSearch("index1", "lib3")
//                    .setQuery(queryBuilder)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index ------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ---------->:" + key + ", value ---------->:" + value);
//                });
//                System.out.println("======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 精确匹配查询
//     * termQuery查询
//     */
//    @Test
//    public void testTermQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            QueryBuilder queryBuilder = QueryBuilders.termQuery("interests", "weiqi");
//            SearchResponse response = client.prepareSearch("lib3")
//                    .setQuery(queryBuilder)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index -------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ---------->:" + key + ", value ---------->:" + value);
//                });
//                System.out.println("======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 精确匹配查询
//     * termsQuery查询
//     */
//    @Test
//    public void testTermsQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            QueryBuilder queryBuilder = QueryBuilders.termsQuery("interests", "weiqi", "kanshu");
//            SearchResponse response = client.prepareSearch("lib3")
//                    .setQuery(queryBuilder)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index --------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ---------->:" + key + ", value ---------->:" + value);
//                });
//                System.out.println("======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 范围查询：
//     * rangeQuery
//     */
//    @Test
//    public void testRangeQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            QueryBuilder queryBuilder = QueryBuilders.rangeQuery("birthday").from("1990-01-01").to("1995-12-30");
//            SearchResponse response = client.prepareSearch("lib3")
//                    .setQuery(queryBuilder)
//                    .addSort("birthday", SortOrder.ASC)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index ---------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ---------->:" + key + ", value ---------->:" + value);
//                });
//                System.out.println("=======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 前缀查询：
//     * prefixQuery
//     */
//    @Test
//    public void prefixQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            QueryBuilder queryBuilder = QueryBuilders.prefixQuery("name", "li");
//            SearchResponse response = client.prepareSearch("lib3")
//                    .setQuery(queryBuilder)
//                    .addSort("birthday", SortOrder.ASC)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index --------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ---------->:" + key + ", value ----------->:" + value);
//                });
//                System.out.println("======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 通配符查询,模糊查询
//     * wildcardQuery    fuzzyQuery
//     */
//    @Test
//    public void testTongPeiFuQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            //通配符查询
////            QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name", "li*");
////            SearchResponse response = client.prepareSearch("lib3")
////                    .setQuery(queryBuilder)
////                    .addSort("birthday", SortOrder.ASC)
////                    .setSize(10).get();
//
//            //模糊查询 不用完全一样,只要写出部分就可以了
//            QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("name", "hongzh");
//            SearchResponse response = client.prepareSearch("lib3")
//                    .setQuery(queryBuilder)
//                    .addSort("birthday", SortOrder.ASC)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index ---------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ---------->:" + key + ", value ----------->:" + value);
//                });
//                System.out.println("======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Type 类型查询, 查询index 下 哪个type的 所有数据
//     */
//    @Test
//    public void testTypeQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            QueryBuilder queryBuilder = QueryBuilders.typeQuery("blog");
//            SearchResponse response = client.prepareSearch("index1")
//                    .setQuery(queryBuilder)
//                    .addSort("id", SortOrder.ASC)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index --------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ----------->:" + key + ", value ---------->:" + value);
//                });
//                System.out.println("======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 指定Ids id集合的查询
//     * ids查询
//     */
//    @Test
//    public void testIdsQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            QueryBuilder queryBuilder = QueryBuilders.idsQuery("user").addIds("1", "2", "3");
//            SearchResponse response = client.prepareSearch("lib3")
//                    .setQuery(queryBuilder)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index ---------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ----------->:" + key + ", value ---------->:" + value);
//                });
//                System.out.println("======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 聚合查询:
//     */
//    @Test
//    public void testJuheQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            //1.求age最大值
//            AggregationBuilder aggregationBuilder = AggregationBuilders.max("ageMax").field("age");
//            SearchResponse response = client.prepareSearch("lib3")
//                    .addAggregation(aggregationBuilder)
//                    .get();
//            Max max = response.getAggregations().get("ageMax");
//            System.out.println("年龄最大值 ageMax--------------------------------->:" + max.getValue());
//
//            //2.求age最小值
//            AggregationBuilder aggregationBuilder1 = AggregationBuilders.min("ageMin").field("age");
//            SearchResponse response1 = client.prepareSearch("lib3")
//                    .addAggregation(aggregationBuilder1)
//                    .get();
//            Min min = response1.getAggregations().get("ageMin");
//            System.out.println("年龄最大值 ageMin--------------------------------->:" + min.getValue());
//
//            //3.求age的平均值
//            AggregationBuilder aggregationBuilder2 = AggregationBuilders.avg("ageAvg").field("age");
//            SearchResponse response2 = client.prepareSearch("lib3")
//                    .addAggregation(aggregationBuilder2)
//                    .get();
//            Avg avg = response2.getAggregations().get("ageAvg");
//            System.out.println("年龄平均值 ageAvg--------------------------------->:" + avg.getValue());
//
//            //4.求age总和
//            AggregationBuilder aggregationBuilder3 = AggregationBuilders.sum("ageSum").field("age");
//            SearchResponse response3 = client.prepareSearch("lib3")
//                    .addAggregation(aggregationBuilder3)
//                    .get();
//            Sum sum = response3.getAggregations().get("ageSum");
//            System.out.println("年龄最大值 ageSum--------------------------------->:" + sum.getValue());
//
//            //5.求基数的 : 类似与 distinct
//            AggregationBuilder aggregationBuilder4 = AggregationBuilders.cardinality("ageCardinality").field("age");
//            SearchResponse response4 = client.prepareSearch("lib3")
//                    .addAggregation(aggregationBuilder4)
//                    .get();
//            Cardinality cardinality = response4.getAggregations().get("ageCardinality");
//            System.out.println("年龄的基数[互不相同的] ageCardinality-------------->:" + cardinality.getValue());
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 测试
//     * query string 查询
//     */
//    @Test
//    public void testQueryString() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            //精确匹配 name 叫 zhangsan 的查询
////            QueryBuilder queryBuilder = QueryBuilders.commonTermsQuery("name", "zhangsan");
////            SearchResponse response = client.prepareSearch("lib3")
////                    .setQuery(queryBuilder)
////                    .setSize(10).get();
//
//            //精确匹配 所有字段中必须有xiangqi 必须没有 weiqi 的查询
////            QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("+xiangqi -weiqi");
////            SearchResponse response = client.prepareSearch("lib3")
////                    .setQuery(queryBuilder)
////                    .setSize(10).get();
//
//            //非精确匹配 所有字段中 满足其中一个就可以 查询出来
//            QueryBuilder queryBuilder = QueryBuilders.simpleQueryStringQuery("+xiangqi -weiqi");
//            SearchResponse response = client.prepareSearch("lib3")
//                    .setQuery(queryBuilder)
//                    .setSize(10).get();
//
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index ---------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ----------->:" + key + ", value ---------->:" + value);
//                });
//                System.out.println("=====================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 组合查询:
//     * bool query
//     */
//    @Test
//    public void testBoolQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
////            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
////                    .must(QueryBuilders.matchQuery("interests", "xiaqi"))
////                    .mustNot(QueryBuilders.matchQuery("interests", "lvyou"))
////                    .should(QueryBuilders.matchQuery("address", "bei jing"))
////                    .filter(QueryBuilders.rangeQuery("birthday").gt("1990-01-01").format("yyyy-MM-dd"));
//
//            //不计算 相关分数的 查询
//            QueryBuilder queryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("name", "zhangsan"));
//            SearchResponse response = client.prepareSearch("lib3")
//                    .setQuery(queryBuilder)
//                    .setSize(10).get();
//
//            SearchHits hits = response.getHits();
//            hits.forEach(hit -> {
//                System.out.println("index ----------------------------------------------------------->:" + hit.getIndex());
//                Map<String, Object> indexMap = hit.getSourceAsMap();
//                indexMap.forEach((key, value) -> {
//                    System.out.println("key ----------->:" + key + ", value ---------->:" + value);
//                });
//                System.out.println("======================================================================================");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 复杂的聚合操作
//     * 分组聚合操作 terms aggregation
//     */
//    @Test
//    public void testFuZaJuHeQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
//            SearchResponse response = client.prepareSearch("lib3")
//                    .addAggregation(aggregationBuilder)
//                    .execute()
//                    .actionGet();
//
//            Terms terms = response.getAggregations().get("ageGroup");
//            terms.getBuckets().forEach(bucket -> {
//                System.out.println(bucket.getKey() + " : " + bucket.getDocCount());
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 过滤聚合 filter Aggregation
//     */
//    @Test
//    public void testFilterAggregation() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//
//            QueryBuilder queryBuilder = QueryBuilders.termQuery("age", "20");
//            AggregationBuilder aggregationBuilder = AggregationBuilders.filter("ageFilter", queryBuilder);
//
//            SearchResponse response = client.prepareSearch("lib3")
//                    .addAggregation(aggregationBuilder)
//                    .execute()
//                    .actionGet();
//
//            Filter filter = response.getAggregations().get("ageFilter");
//            System.out.println("年龄在20岁的人数一共:" + filter.getDocCount() + "个");
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * filters 过滤 多个Key的 分组类似的
//     */
//    @Test
//    public void testFiltersAggregation() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//
//            AggregationBuilder aggregationBuilder = AggregationBuilders.filters("filters",
//                    new FiltersAggregator.KeyedFilter("chang ge", QueryBuilders.termQuery("interests", "chang")),
//                    new FiltersAggregator.KeyedFilter("he jiu", QueryBuilders.termQuery("interests", "he"))
//            );
//
//            SearchResponse response = client.prepareSearch("lib3")
//                    .addAggregation(aggregationBuilder)
//                    .execute()
//                    .actionGet();
//
//            Filters filters = response.getAggregations().get("filters");
//            filters.getBuckets().forEach(bucket -> {
//                System.out.println(bucket.getKey() + " : " + bucket.getDocCount());
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Range Aggregation 范围聚合查询
//     */
//    @Test
//    public void testRangeAggregation() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            //统计三个 范围的查询
//            AggregationBuilder aggregationBuilder = AggregationBuilders.range("range")
//                    .field("age")
//                    .addUnboundedTo(50)     //(, to)
//                    .addRange(25, 50)       //[from, to)
//                    .addUnboundedFrom(25);  //(from,  )
//
//            SearchResponse response = client.prepareSearch("lib3")
//                    .addAggregation(aggregationBuilder)
//                    .execute()
//                    .actionGet();
//
//            Range range = response.getAggregations().get("range");
//            range.getBuckets().forEach(bucket -> {
//                System.out.println(bucket.getKey() + " : " + bucket.getDocCount());
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 查询 空字段的文档信息
//     * missing Query
//     */
//    @Test
//    public void testMissingQuery() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            AggregationBuilder aggregationBuilder = AggregationBuilders.missing("missing").field("price");
//            SearchResponse response = client.prepareSearch("lib4")
//                    .addAggregation(aggregationBuilder)
//                    .execute()
//                    .actionGet();
//
//            Missing missing = response.getAggregations().get("missing");
//            System.out.println(missing.getDocCount());
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 代码查看集群中状态的代码
//     * cluster status
//     */
//    @Test
//    public void testClusterHealth() {
//        //指定ES集群
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        try {
//            //获取链接集群的客户端
//            TransportClient client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.120.181"), 9300));
//
////                    .addTransportAddresses(
////                            new TransportAddress(InetAddress.getByName("192.168.120.126"), 9300),
////                            new TransportAddress(InetAddress.getByName("192.168.120.184"), 9300));
//
//            ClusterHealthResponse response = client.admin().cluster().prepareHealth().get();
//
//            System.out.println("clusterName  ----------------------->:" + response.getClusterName());
//            System.out.println("numberOfNodes ---------------------->:" + response.getNumberOfNodes());
//            System.out.println("numberOfDataNodes ------------------>:" + response.getNumberOfDataNodes());
//
//            System.out.println("**********************************************************************************");
//
//            response.getIndices().values().forEach(health -> {
//                System.out.println("index ----------------------->:" + health.getIndex());
//                System.out.println(health.getNumberOfShards());
//                System.out.println(health.getNumberOfReplicas());
//                System.out.println(health.getStatus().toString());
//                System.out.println("**********************************************************************************");
//            });
//
//            //关闭client端
//            client.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
