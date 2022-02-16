package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class post_request_send_complex_json {
    ResponseSpecification customResponeSpecification;
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://a7654ec8-0542-4bb0-9e59-4171712ef559.mock.pstmn.io").
                addHeader("x-mock-match-request-body","true").
                /*setConfig(config.encoderConfig(EncoderConfig.encoderConfig().
                        appendDefaultContentCharsetToContentTypeIfUndefined(false))).*/
                        setContentType("application/json; charset=utf-8")
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponeSpecification = responseSpecBuilder.build();
    }
    @Test
    public void validate_post_json_array_as_list(){
        List<Integer> iArrayList = new ArrayList<>();
        iArrayList.add(5);
        iArrayList.add(9);
        HashMap<String,Object> batterHashMap2 = new HashMap<>();
        batterHashMap2.put("id",iArrayList);
        batterHashMap2.put("type","Chocolate");
        HashMap<String,Object> batterHashMap1 = new HashMap<>();
        batterHashMap1.put("id","1001");
        batterHashMap1.put("type","Regular");

        List<HashMap<String,Object>> batterArrayList = new ArrayList<HashMap<String,Object>>();
        batterArrayList.add(batterHashMap1);
        batterArrayList.add(batterHashMap2);
        HashMap<String,List<HashMap<String,Object>>> battersHashMap = new HashMap<String,List<HashMap<String,Object>>>();
        battersHashMap.put("batter",batterArrayList);

        List<String> typeArrayList = new ArrayList<String>();
        typeArrayList.add("test1");
        typeArrayList.add("test2");

        HashMap<String,Object> toppingHashmap2 = new HashMap<>();
        toppingHashmap2.put("id","5002");
        toppingHashmap2.put("type",typeArrayList);

        HashMap<String,Object> toppingHashmap1 = new HashMap<>();
        toppingHashmap1.put("id","5001");
        toppingHashmap1.put("type","None");

        List<HashMap<String,Object>> toppingArrayList = new ArrayList<HashMap<String,Object>>();
        toppingArrayList.add(toppingHashmap1);
        toppingArrayList.add(toppingHashmap2);
        HashMap<String,Object> mainHashMap = new HashMap<String,Object>();
        mainHashMap.put("id","0001");
        mainHashMap.put("type","donut");
        mainHashMap.put("name","Cake");
        mainHashMap.put("ppu",0.55);
        mainHashMap.put("batters",battersHashMap);
        mainHashMap.put("topping",toppingArrayList);
        given().
                body(mainHashMap).
                when().
                post("/postComplexJson").
                then().spec(customResponeSpecification)
                .assertThat().body("msg",equalTo("success"));
    }
}
