package com.test;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
public class RequestSpecificationExample {

    @Test
    public void validate_status_code(){
        RequestSpecification requestSpecification = given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c");
        given().spec(requestSpecification).
                when().
                    get("/workspaces").
                then().
                    log().all().
                    assertThat().
                    statusCode(200)
                ;

    }
}
