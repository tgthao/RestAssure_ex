package com.test;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static  io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class AutomateGet02 {
    @Test
    public void extract_response(){
        Response res = given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c")
                .when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println("response: " +res.asString() );
    }


}
