package com.inspur;

import com.inspur.entity.Item;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: demo
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-14 19:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ItcastElasticSearchApplication.class})
public class ElasticSearchTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * 创建索引Index和 Mapping映射的关系
     */
    @Test
    public void testCreateIndexAndMapping() {
        this.elasticsearchTemplate.createIndex(Item.class);
        this.elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void testCreateItem() {
        Item item = new Item(1L, "小米手机", "手机",
                "小米", 3599.00, "http://image.leyou.com/13123.jpg");
        itemRepository.save(item);
    }

    @Test
    public void addBatchItem() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    @Test
    public void testFind() {
        Item item = this.itemRepository.findById(1L).get();
        System.out.println(item);
    }

    @Test
    public void testFindAllBySort() {
        Iterable<Item> iterable = this.itemRepository.findAll(Sort.by("price").descending());
        iterable.forEach(System.out::println);
    }

    /**
     * 自定义方法的实现
     */
    @Test
    public void testFindBy() {
//        List<Item> itemList = this.itemRepository.findByTitle("手机");
        List<Item> itemList = this.itemRepository.findByPriceBetween(3699d, 4499d);
        itemList.forEach(System.out::println);
    }

    @Test
    public void testSearch() {
        MatchQueryBuilder builder = QueryBuilders.matchQuery("title", "手机");
        itemRepository.search(builder).forEach(System.out::println);

    }

    /**
     * 自定义高级查询
     */
    @Test
    public void testNativeQuery() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));
        Page<Item> itemPage = this.itemRepository.search(queryBuilder.build());
        System.out.println(itemPage.getTotalPages());
        System.out.println(itemPage.getTotalElements());
        itemPage.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void testNativePageQuery(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));

        // 初始化分页参数, 页码从0 开始
        int page = 0;
        int size = 3;
        // 设置分页参数
        queryBuilder.withPageable(PageRequest.of(page, size));

        // 执行搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 打印总条数
        System.out.println(items.getTotalElements());
        // 打印总页数
        System.out.println(items.getTotalPages());
        // 每页大小
        System.out.println(items.getSize());
        // 当前页
        System.out.println(items.getNumber());
        items.forEach(System.out::println);
    }

    /**
     * 排序查询
     */
    @Test
    public void testSort(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));

        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));

        // 执行搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 打印总条数
        System.out.println(items.getTotalElements());
        items.forEach(System.out::println);
    }

    /**
     * 聚合查询
     */
    @Test
    public void testAggs() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.addAggregation(AggregationBuilders.terms("aggBrand").field("brand"));
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        //执行聚合查询
        AggregatedPage<Item> aggregatedPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        Terms terms = (Terms) aggregatedPage.getAggregation("aggBrand");
        terms.getBuckets().forEach(bucket -> {
            System.out.println(bucket.getKey() + " : " + bucket.getDocCount());
        });
    }

    /**
     * 聚合查询,桶内度量
     */
    @Test
    public void testBucketAggs() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder
                .addAggregation(
                        AggregationBuilders.terms("aggBrand").field("brand")
                        .subAggregation( AggregationBuilders.avg("avgPrice").field("price"))
                );
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        //执行聚合查询
        AggregatedPage<Item> aggregatedPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        Terms terms = (Terms) aggregatedPage.getAggregation("aggBrand");
        terms.getBuckets().forEach(bucket -> {
            System.out.println(bucket.getKey() + " : " + bucket.getDocCount());
            Avg avgPrice = bucket.getAggregations().get("avgPrice");
            System.out.println(avgPrice.getName() + " : " + avgPrice.getValueAsString());
        });
    }
}
