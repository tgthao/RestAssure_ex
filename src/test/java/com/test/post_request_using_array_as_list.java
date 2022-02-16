package com.test;

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


public class post_request_using_array_as_list {
    ResponseSpecification customResponeSpecification;
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
        .setBaseUri("https://8f6d7436-aba9-4c1f-bc81-fdc881a11fb1.mock.pstmn.io").
                addHeader("x-mock-match-request-body","true").

                setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).log(LogDetail.ALL);
        customResponeSpecification = responseSpecBuilder.build();
    }
    @Test
    public void validate_post_json_array_as_list(){
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id","5001");
                hashMap.put("type","none");
                HashMap<String,String> hashMap2 = new HashMap<>();
                hashMap2.put("id","5002");
                hashMap2.put("type","Glazed");

                List<Map> jsonList = new ArrayList<Map>();
                jsonList.add(hashMap);
                jsonList.add(hashMap2);
                given().
                        body(jsonList).
                when().
                    post("/post").
                then().spec(customResponeSpecification)
                        .assertThat().body("msg",equalTo("Success"));
    }
}
