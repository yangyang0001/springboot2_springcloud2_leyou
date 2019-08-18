package com.inspur.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


/**
 * @program: demo
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-14 19:07
 */

@Document(indexName = "item", type = "docs", shards = 1, replicas = 0)
@Data
public class Item implements Serializable {
    private static final long serialVersionUID = -5785938423325468722L;

    @Id
    Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    String title; //标题

    @Field(type = FieldType.Keyword)
    String category;// 分类

    @Field(type = FieldType.Keyword)
    String brand; // 品牌

    @Field(type = FieldType.Double)
    Double price; // 价格

    @Field(type = FieldType.Keyword, index = false)
    String images; // 图片地址


    public Item() {}

    public Item(Long id, String title, String category, String brand, Double price, String images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.images = images;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", images='" + images + '\'' +
                '}';
    }
}
