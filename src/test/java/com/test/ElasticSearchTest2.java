package com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.IntStream;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by Administrator on 2017/7/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ElasticSearchTest2 {

    @Autowired
    private ElasticsearchTemplate template;
    private static final String indexName = "postindex";
    private static final String typeName =  "posttype";

    @Test
    public void testCreateIndex() {
        template.deleteIndex(indexName);
        boolean isCreated = template.createIndex(indexName);
        System.out.println("isCreated=" + isCreated);
    }

    @Test
    public void testGetIndex() {
        Map setting = template.getSetting(indexName);
        System.out.println(JSON.toJSONString(setting, SerializerFeature.PrettyFormat));
    }

    @Test
    public void testCreateMapping() {
        XContentBuilder builder = getMapping();
        boolean isCreated = template.putMapping(indexName, typeName, builder);
        System.out.println("isCreated=" + isCreated);
    }

    private XContentBuilder getMapping() {
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    //开启倒计时功能
                    .startObject("_ttl")
                    .field("enabled",false)
                    .endObject()
                    .startObject("properties")
                    .startObject("id")
                    .field("type","long")
                    .field("index","not_analyzed")
                    .endObject()
                    .startObject("content")
                    .field("type","string")
                    .endObject()
                    .startObject("pic_url")
                    .field("type","string")
                    .field("index","no")
                    .endObject()
                    .startObject("topic")
                    .field("type","string")
                    .field("index","not_analyzed")
                    .endObject()
                    .startObject("user_id")
                    .field("type","long")
                    .field("index","not_analyzed")
                    .endObject()
                    .startObject("user_name")
                    .field("type","string")
                    .field("index","analyzed")
                    .endObject()
                    .startObject("user_mobile")
                    .field("type","string")
                    .field("index","not_analyzed")
                    .endObject()
                    .startObject("view_count")
                    .field("type","long")
                    .endObject()
                    .startObject("like_count")
                    .field("type","long")
                    .endObject()
                    .startObject("create_time")
                    .field("type","date")
                    .field("format","yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd HH:mm:ss||yyyy-MM-dd")
                    .endObject()
                    .startObject("status")
                    .field("type","integer")
                    .field("index","not_analyzed")
                    .endObject()
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapping;
    }

    @Test
    public void testMapping() {
        Map mapping = template.getMapping(indexName,typeName);
        System.out.println(JSON.toJSONString(mapping, SerializerFeature.PrettyFormat));
    }

    @Test
    public void testCreateData() {
        Long[] userIdArr = new Long[] {1231829823L, 12312349095L, 1298398934L,394839859545L, 29102903918384L, 18239819283L,4934839123123L, 1239485983982L};
        BulkRequestBuilder bulkRequest = template.getClient().prepareBulk().setRefresh(false);

        for (int num = 1000000; num < 10000000; num ++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", num);
            data.put("content", RandomStringUtils.random(RandomUtils.nextInt(180)));
            data.put("pic_url", "http://wwww.baidu.com");
            //data.put("topic", JSON.toJSON(Arrays.asList("棋我人","工人我仍")));
            data.put("topic",Arrays.asList("棋我人","工人我仍"));
            data.put("user_id", userIdArr[num % userIdArr.length]);
            String name = new StringBuilder(getChinese()).append(getChinese()).append(getChinese()).toString();
            data.put("user_name", name + num);
            data.put("user_mobile", "1213131313" + num);
            data.put("create_time", this.randomDateBetweenMinAndMax());
            data.put("view_count", RandomUtils.nextInt(10000));
            data.put("like_count", RandomUtils.nextInt(10000));
            data.put("status", 1);
            IndexRequestBuilder indexRequest = template.getClient().prepareIndex(indexName, typeName, String.valueOf(num)).setSource(data);
            bulkRequest.add(indexRequest);
            if (bulkRequest.numberOfActions() % 10000 == 0) {
                BulkResponse response = bulkRequest.execute().actionGet();
                System.out.println(response.hasFailures());
                bulkRequest = template.getClient().prepareBulk().setRefresh(false);
            }
        }

        if (bulkRequest.numberOfActions() > 0) {
            BulkResponse response = bulkRequest.execute().actionGet();
            System.out.println(response.hasFailures());
        }
    }

    private String randomDateBetweenMinAndMax(){
        Calendar calendar = Calendar.getInstance();
        //根据需求，这里要将时分秒设置为0
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        long min = calendar.getTime().getTime();
        calendar.set(Calendar.DATE, 17);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.getTime().getTime();
        long max = calendar.getTime().getTime();
        //得到大于等于min小于max的double值
        double randomDate = Math.random()*(max-min)+min;
        //将double值舍入为整数，转化成long类型
        calendar.setTimeInMillis(Math.round(randomDate));

        return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS").format(calendar);
        //return calendar.getTime().getTime();
    }

    private String getChinese() {
        String str = null;
        int highPos, lowPos;
        Random random = new Random();
        highPos = (176 + Math.abs(random.nextInt(71)));//区码，0xA0打头，从第16区开始，即0xB0=11*16=176,16~55一级汉字，56~87二级汉字
        random=new Random();
        lowPos = 161 + Math.abs(random.nextInt(94));//位码，0xA0打头，范围第1~94列

        byte[] bArr = new byte[2];
        bArr[0] = (new Integer(highPos)).byteValue();
        bArr[1] = (new Integer(lowPos)).byteValue();
        try {
            str = new String(bArr, "GB2312");   //区位码组合成汉字
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Test
    public void testSearchId() {
        IntStream.range(1, 10).forEach(var -> {
            SearchResponse response = template.getClient()
                .prepareSearch(indexName)
                .setTypes(typeName)
                .setQuery(boolQuery().must(termQuery("id", RandomUtils.nextInt(10000000))))
                .execute()
                .actionGet();

        });
    }

    @Test
    public void testSeachTopic() {
        IntStream.range(1, 10).forEach(var -> {
            SearchResponse response = template.getClient()
            .prepareSearch(indexName)
            .setTypes(typeName)
            .setQuery(boolQuery().must(termQuery("topic", "棋我人")))
            .execute()
            .actionGet();
        });
    }

    @Test
    public void testSeachContent() {
        IntStream.range(1, 10).forEach(var -> {
            SearchResponse response = template.getClient()
                .prepareSearch(indexName)
                .setTypes(typeName)
                .setQuery(matchQuery("content", "遥控器"))
                .execute()
                .actionGet();
        });
    }

    @Test
    public void testDeleteId() {
        DeleteRequestBuilder deleteRequest = template.getClient()
                .prepareDelete()
                .setIndex(indexName)
                .setType(typeName)
                .setRefresh(true)
                .setId("1");

        DeleteResponse response = deleteRequest.execute().actionGet();
        System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
    }

    @Test
    public void testSearchCreateTM() {
        String endTime = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        String endTime2 = "2017-07-16 23:59:57.471";
        IntStream.range(1, 10).forEach(var -> {
            SearchResponse response = template.getClient()
                    .prepareSearch(indexName)
                    .setTypes(typeName)
                    .setQuery(boolQuery()
                            .must(rangeQuery("create_time").lt(endTime2))
                            .must(termQuery("status", 1)))
                    .addSort("create_time", SortOrder.DESC)
                    .setSize(10)
                    .setFrom(0)
                    .execute()
                    .actionGet();
            //SearchHits searchHits = response.getHits();
            //for (SearchHit hit : searchHits) {
            //    System.out.println(hit.getSource().get("create_time"));
            //}
        });
    }

    @Test
    public void testSearchCreateTMOrderByCount() {
        String endTime = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        String endTime2 = "2017-07-16 23:59:57.471";
        IntStream.range(1, 10).forEach(var -> {
            SearchResponse response = template.getClient()
                    .prepareSearch(indexName)
                    .setTypes(typeName)
                    .setQuery(boolQuery()
                            .must(rangeQuery("create_time").lt(endTime2))
                            .must(termQuery("status", 1)))
                    .addSort("view_count", SortOrder.DESC)
                    .setSize(10)
                    .setFrom(0)
                    .execute()
                    .actionGet();
            //SearchHits searchHits = response.getHits();
            //for (SearchHit hit : searchHits) {
            //    System.out.println(hit.getSource().get("create_time"));
            //}
        });
    }
}
