package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.collection.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ComplexPojoTest {
    ResponseSpecification customResponeSpecification;
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com")
                .addHeader("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c")
                .setContentType("application/json; charset=utf-8")
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponeSpecification = responseSpecBuilder.build();
    }
    @Test
    public void simple_pojo_example() throws JsonProcessingException, JSONException {
        Header header = new Header("Content-type","application/json");
        List<Header> headerList = new ArrayList<>();
        headerList.add(header);
        Body body = new Body("raw","{\"data\":\"123\"}");
        RequestRequest request = new RequestRequest("https://postman-echo.com/post","POST"
                ,headerList,body,"This is sample POST Request");

        RequestRootRequest requestRoot = new RequestRootRequest("Sample POST Request",request);
        List<RequestRootRequest> requestRootList = new ArrayList<>();
        requestRootList.add(requestRoot);

        FolderRequest folder = new FolderRequest("This is a folder",requestRootList);
        List<FolderRequest> folderList = new ArrayList<>();
        folderList.add(folder);

        Info info = new Info("Sample Collection1","This is just a sample collection"
                ,"https://schema.getpostman.com/json/collection/v2.1.0/collection.json");
        CollectionRequest collection = new CollectionRequest(info,folderList);

        CollectionRootRequest collectionRoot = new CollectionRootRequest(collection);
        String collectionUid = given()
                .body(collectionRoot)
        .when().
                    post("/collections").
        then().
                    spec(customResponeSpecification)
                .extract()
                .response()
                       .path("collection.uid");

        CollectionRootResponse deserializedCollectionRoot = given()
                .pathParam("collectionUid",collectionUid)
                .when()
                .get("/collections/{collectionUid}")
                .then().spec(customResponeSpecification)
                .extract()
                .response()
                .as(CollectionRootResponse.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
        String deserializedCollectionRootStr = objectMapper.writeValueAsString(deserializedCollectionRoot);

        JSONAssert.assertEquals(collectionRootStr,deserializedCollectionRootStr,
                new CustomComparator(JSONCompareMode.STRICT_ORDER,
                        new Customization("collection.item[*].item[*]",
                        //new Customization("collection.item[*].item[*].request.url",
                                (a1, a2) -> true)));
        List<String> UrlRequestList = new ArrayList<>();
        List<String > UrlResponseList = new ArrayList<>();
        for (RequestRootRequest requestRootRequest:requestRootList){
            System.out.println("Url from request payload: "+requestRootRequest.getRequest().getUrl());
            //UrlRequestList.add(requestRootRequest.getRequest().getUrl());
            UrlRequestList.add("24h.com.vn");
        }
        List<FolderResponse> folderResponseList = deserializedCollectionRoot.getCollection().getItem();
        for(FolderResponse folderResponse : folderResponseList){
            List<RequestRootResponse> requestRootResponseList = folderResponse.getItem();
            for (RequestRootResponse requestRootResponse : requestRootResponseList){
            System.out.println("Url from response: "+requestRootResponse.getRequest().getUrl().getRaw());
                Url url = requestRootResponse.getRequest().getUrl();
                url.getRaw();
                UrlResponseList.add(url.getRaw());
            }
        }
        assertThat(UrlResponseList,containsInAnyOrder(UrlRequestList.toArray()));
    }
}
