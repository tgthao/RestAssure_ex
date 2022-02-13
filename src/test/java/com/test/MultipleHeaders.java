package com.test;

import io.restassured.config.LogConfig;
import io.restassured.http.Header;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class MultipleHeaders {


@Test
    public void multiple_headers(){
    Header header = new Header("header","value2");
    Header header1 = new Header("x-mock-match-request-headers","header");
                given().
                        baseUri("https://8f6d7436-aba9-4c1f-bc81-fdc881a11fb1.mock.pstmn.io").
                        header(header).
                        header(header1).
                when().
                        get("/get").
                then().
                        log().all().
                        assertThat().
                        statusCode(200);
    }


}
