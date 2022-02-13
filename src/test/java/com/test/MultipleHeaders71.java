package com.test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class MultipleHeaders71 {


@Test
    public void multiple_headers(){
    Header header = new Header("header","value2");
    Header header1 = new Header("x-mock-match-request-headers","header");
    Headers headers = new Headers(header, header1);
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
