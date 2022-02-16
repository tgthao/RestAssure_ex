package com.test;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class RequestParameters {
    public void single_query_parameters() {
        given().
                baseUri("https://postman-echo.com")
                //.param("foo1","bar1")
                /*Query param metter*/
                .queryParam("foo1", "bar1")
                // .log().all()
                .when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }
    public void multiple_query_parameters() {
        HashMap<String, String> queryHashMap = new HashMap<>();
        queryHashMap.put("foo1", "bar1");
        queryHashMap.put("foo2", "bar2");
        given().
                baseUri("https://postman-echo.com")
                .param("foo1", "bar1", "bar2")
                .param("foo2", "bar1;bar2")
                /*Query param metter*/
                /* .queryParam("foo1","bar1")
                 .queryParam("foo2","bar2")*/
                //.queryParams(queryHashMap)
                .log().all()
                .when().
                    get("/get").
                then().
                    log().all().
                    assertThat().
                    statusCode(200);

    }
    @Test
    public void multipart_from_data() {
        given().
                baseUri("https://postman-echo.com")
                .multiPart("foo1","bar1")
                .multiPart("foo2","bar2")
                .log().all()
                .when().
                    post("/post/").
                then().
                    log().all().
                    assertThat().
                    statusCode(200);

    }

}
