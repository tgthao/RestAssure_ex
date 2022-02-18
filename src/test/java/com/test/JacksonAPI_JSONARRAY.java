package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;


public class JacksonAPI_JSONARRAY {
    ResponseSpecification customResponeSpecification;
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com").
                addHeader("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c").
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



    public void validate_post_request_payload_as_json_array_list() throws JsonProcessingException {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id","5001");
        hashMap.put("type","None");
        HashMap<String,String> hashMap2 = new HashMap<>();
        hashMap2.put("id","5002");
        hashMap2.put("type","Glazed");

        List<HashMap<String,String>> jsonList = new ArrayList<>();
        jsonList.add(hashMap);
        jsonList.add(hashMap2);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonListStr = objectMapper.writeValueAsString(jsonList);

        given().
                body(jsonListStr).
                when().
                post("/post").
                then().spec(customResponeSpecification)
                .assertThat().body("msg",equalTo("success"));
    }
    @Test
    public void serialize_json_using_jackson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode nestedObjectNode = objectMapper.createObjectNode();

        nestedObjectNode.put("name","My Name API UAT Rest Assured");
        nestedObjectNode.put("type","personal");
        nestedObjectNode.put("description","My Name API UAT created by GT");

        ObjectNode  mainObjectNode = objectMapper.createObjectNode();
        mainObjectNode.set("workspace",nestedObjectNode);
        String mainObjectStr = objectMapper.writeValueAsString(mainObjectNode);


        given().
                body(mainObjectStr).
                when().
                post("/workspaces").
                then().spec(customResponeSpecification)
                .assertThat().body("workspace.name",equalTo("My Name API UAT Rest Assured"));
    }
}
