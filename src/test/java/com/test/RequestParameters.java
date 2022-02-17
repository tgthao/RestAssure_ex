package com.test;

import org.testng.annotations.Test;

import java.io.File;
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
    @Test
    public void upload_file_multipart_form_data() {
        String attributes = "{\n" +
                "  \"instructions\": [\n" +
                "    \"GroovyExample.txt\",\n" +
                "    \"Select an item to view its path.\",\n" +
                "    \"Replace 'x' with the name of your variable.\"\n" +
                "  ]\n" +
                "}";
        given().
                baseUri("https://postman-echo.com")
                .multiPart("file",new File("src/main/resources/GroovyExample.txt"))
                .multiPart("attributes",attributes,"application/json")
                .log().all()
                .when().
                    post("/post/").
                then().
                    log().all().
                    assertThat().
                    statusCode(200);

    }

}
