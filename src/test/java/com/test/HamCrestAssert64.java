package com.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class HamCrestAssert64 {
    @Test
    public void validate_response_body_hamcrest_learnings(){
         given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c").
                 log().all()
                .when().
                    get("/workspaces").
                then().
                log().ifError().
                assertThat().
                statusCode(200);
    }    @Test
    public void logIfError(){
         given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c").
                 log().all()
                .when().
                    get("/workspaces").
                then().
                log().ifError().
                assertThat().
                statusCode(2001);
    }


}
