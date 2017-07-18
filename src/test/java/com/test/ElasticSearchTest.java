package com.test;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author chenzhichao 000178
 * @date 2017/7/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ElasticSearchTest {
    @Autowired
    private ElasticsearchTemplate template;
    private static final String indexName = "thingindex";
    @Test
    public void testIndex() {
       boolean isCreated = template.createIndex(indexName);
       System.out.println(isCreated);
    }

    @Test
    public void testAddMappings() {
        XContentBuilder builder = getMapping();
        template.putMapping(indexName, "thingtype", builder);
    }

    public static XContentBuilder getMapping(){
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    //开启倒计时功能
                    .startObject("_ttl")
                    .field("enabled",false)
                    .endObject()
                    .startObject("_all")
                    .field("enabled",false)
                    .endObject()
                    .startObject("properties")
                    .startObject("id")
                    .field("type","long")
                    .endObject()
                    .startObject("content")
                    .field("type","string")
                    .field("index","analyzed")
                    .endObject()
                    .startObject("media")
                    .field("type","string")
                    .field("index","not_analyzed")
                    .endObject()
                    .startObject("media_type")
                    .field("type","integer")
                    .field("index","not_analyzed")
                    .endObject()
                    .startObject("preview_pic")
                    .field("type","string")
                    .field("index","no")
                    .endObject()
                    .startObject("longitude")
                    .field("type","double")
                    .field("index","not_analyzed")
                    .endObject()
                    .startObject("latitude")
                    .field("type","double")
                    .field("index","not_analyzed")
                    .endObject()
                    .startObject("address")
                    .field("type","string")
                    .field("index","no")
                    .endObject()
                    .startObject("status")
                    .field("type","integer")
                    .endObject()
                    .startObject("floor")
                    .field("type","integer")
                    .endObject()
                    .startObject("fixed_start_date")
                    .field("type","date")
                    .field("format","yyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd")
                    .endObject()
                    .startObject("fixed_end_date")
                    .field("type","date")
                    .field("format","yyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd")
                    .endObject()
                    .startObject("link_type").field("type","integer").endObject()
                    .startObject("link")
                    .field("type","string")
                    .field("index","no")
                    .endObject()
                    .startObject("things_type").field("type","integer").endObject()
                    .startObject("base_view_count").field("type","long").endObject()
                    .startObject("view_count").field("type","long").endObject()
                    .startObject("base_like_count").field("type","long").endObject()
                    .startObject("like_count").field("type","long").endObject()
                    .startObject("topics").field("type","object").endObject()
                    .startObject("publisher_id").field("type","long").endObject()
                    .startObject("publisher")
                    .field("type","string")
                    .field("index","analyzed")
                    .endObject()
                    .startObject("publisher_phone")
                    .field("type","string")
                    .field("index","not_analyzed")
                    .endObject()
                    .startObject("create_time")
                    .field("type","date")
                    .field("format","yyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd")
                    .endObject()
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapping;
    }

    @Test
    public void testGetMapping() {
        Map setting = template.getSetting(indexName);
        System.out.println();
    }
}
