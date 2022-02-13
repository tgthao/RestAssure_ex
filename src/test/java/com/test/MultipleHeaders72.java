package com.test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class MultipleHeaders72 {


@Test
    public void multiple_headers_using_map(){
    HashMap<String,String> headers = new HashMap<String,String>();
    headers.put("header","value2");
    headers.put("x-mock-match-request-headers","header");

                given().
                        baseUri("https://8f6d7436-aba9-4c1f-bc81-fdc881a11fb1.mock.pstmn.io").
                        headers(headers).
                when().
                        get("/get").
                then().
                        log().all().
                        assertThat().
                        statusCode(200);
    }


}
