package com.test;

import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class MultipleHeaders73 {



    public void multiple_headers_using_map(){
    HashMap<String,String> headers = new HashMap<String,String>();
    headers.put("header","value2");
    headers.put("x-mock-match-request-headers","header");

                given().
                        baseUri("https://8f6d7436-aba9-4c1f-bc81-fdc881a11fb1.mock.pstmn.io").
                        header("multiValueHeader","value1","value2").
                        log().headers().
                when().
                        get("/get").
                then().
                        log().all().
                        assertThat().
                        statusCode(200);
    }

    @Test
    void assert_response_headers(){
    HashMap<String,String> headers = new HashMap<String,String>();
    headers.put("header","value2");
    headers.put("x-mock-match-request-headers","header");

                given().
                        baseUri("https://8f6d7436-aba9-4c1f-bc81-fdc881a11fb1.mock.pstmn.io").
                        header("multiValueHeader","value1","value2").
                        log().headers().
                when().
                        get("/get").
                then().
                        log().all().
                        assertThat().
                        statusCode(200).
                        header("responseHeader","resValue2").
                        header("X-RateLimit-Limit","120")
                        ;
    }@Test
    void extract_response_headers_75(){
    HashMap<String,String> headers = new HashMap<String,String>();
    headers.put("header","value2");
    headers.put("x-mock-match-request-headers","header");

    Headers extractedHeaders =           given().
                        baseUri("https://8f6d7436-aba9-4c1f-bc81-fdc881a11fb1.mock.pstmn.io").
                        header("multiValueHeader","value1","value2").
                when().
                        get("/get").
                then().
                        assertThat().
                        statusCode(200).
                        extract().
                        headers()
                        ;
        System.out.println("header name = "+extractedHeaders.get("responseHeader").getName());
        System.out.println("header value = "+extractedHeaders.get("responseHeader").getValue());
        System.out.println("header value = "+extractedHeaders.getValue("responseHeader"));
    }


}