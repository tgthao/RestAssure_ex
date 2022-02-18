package com.test;

import com.rest.pojo.simple.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;

public class SimplePojoTest {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://a7654ec8-0542-4bb0-9e59-4171712ef559.mock.pstmn.io")
                .addHeader("x-mock-match-request-body","true")
                //.addHeader(ContentType.JSON.toString(), "application/json; charset=utf-8")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void simple_pojo_example(){
        SimplePojo simplePojo = new SimplePojo("value1","value2");
        simplePojo.getKey1();
        given().
                body(simplePojo).
                post("/postPOJO").
                then().spec(responseSpecification)
                .assertThat().body("key1",equalTo("value1"),
                        "key2",equalTo("value2"));
    }
}
