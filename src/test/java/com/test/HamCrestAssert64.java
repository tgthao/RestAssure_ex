package com.test;

import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HamCrestAssert64 {

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
    }

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

    public void lofIfValidationFails(){
         given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c").
                 config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .when().
                    get("/workspaces").
                then().
                log().ifValidationFails().
                assertThat().
                statusCode(2001);
    }
@Test
    public void blacklistHeaders(){
        Set<String> headers = new HashSet<String>();
        headers.add("X-API-KEY");
        headers.add("Accept");
         given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c").
                 config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                 log().all()
                .when().
                    get("/workspaces").
                then().
                assertThat().
                statusCode(200);
    }


}
