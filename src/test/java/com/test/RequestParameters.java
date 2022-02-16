package com.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestParameters {
    @Test
            public void single_query_parameters(){
        given().
                baseUri("https://postman-echo.com")
                //.param("foo1","bar1")
                /*Query param metter*/
                .queryParam("foo1","bar1")
               // .log().all()
        .when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }

}
