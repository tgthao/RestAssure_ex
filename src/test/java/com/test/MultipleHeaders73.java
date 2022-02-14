package com.test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

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
    }
    void extract_response_headers_75(){
    HashMap<String,String> headers = new HashMap<String,String>();
    headers.put("header","value1");
    headers.put("x-mock-match-request-headers","header");

    Headers extractedHeaders =           given().
                        baseUri("https://8f6d7436-aba9-4c1f-bc81-fdc881a11fb1.mock.pstmn.io").
                        headers(headers).
            when().
                        get("/get").
                then().
                        assertThat().
                        statusCode(200).
                        extract().
                        headers()
                        ;
        for(Header header:extractedHeaders){
            System.out.println("header name = "+header.getName() +", ");
            System.out.println("header value = "+header.getValue());
        }
     /*   System.out.println("header name = "+extractedHeaders.get("responseHeader").getName());
        System.out.println("header value = "+extractedHeaders.get("responseHeader").getValue());
        System.out.println("header value = "+extractedHeaders.getValue("responseHeader"));*/
    }
    @Test
    void extract_multil_headers_76(){
    HashMap<String,String> headers = new HashMap<String,String>();
    headers.put("header","value1");
    headers.put("x-mock-match-request-headers","header");

        Headers extractedHeaders =           given().
                            baseUri("https://8f6d7436-aba9-4c1f-bc81-fdc881a11fb1.mock.pstmn.io").
                headers(headers).
                when().
                        get("/get").
                then().
                        assertThat().
                        statusCode(200).
                        extract().
                        headers()
                        ;
       List<String> values = extractedHeaders.getValues("multiValueHeader");
       for(String value : values){
           System.out.println(value);
       }
    }


}
