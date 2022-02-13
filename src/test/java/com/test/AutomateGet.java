package com.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static  io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class AutomateGet {
    @Test
    public void validate_status_code(){
        given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c")
                .when().
                get("/workspaces").
                then().
                log().all().
                assertThat().statusCode(201);
    }
    @Test
    public void validate_response_body(){
        given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c")
                .when().
                    get("/workspaces").
                then().
                    log().all().
                    assertThat().
                    statusCode(200).
                    body("workspaces.name",hasItems("My Workspace, NexleSoft, Test Flir, Rest Assure, Team Workspace"),
                            "workspaces.type",hasItems("<[personal, personal, personal, team, team]>"),
                            "workspaces[0].name",equalTo("My Workspace"),
                            "workspaces[0].type",is(equalTo("personal")),
                                    "workspaces.size()",equalTo(5));
    }
}
