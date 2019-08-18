package com.inspur;

import com.inspur.entity.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @program: demo
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-14 19:55
 */
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    public List<Item> findByTitle(String title);
    public List<Item> findByPriceBetween(Double start, Double end);
}
