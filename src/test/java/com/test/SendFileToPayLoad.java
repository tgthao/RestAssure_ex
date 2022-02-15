package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;


public class SendFileToPayLoad {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
        .setBaseUri("https://api.postman.com")
        .addHeader("X-API-KEY", "PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }
    @Test
    public void validate_post_request_bdd_styple(){
        File file = new File("src/main/resources/CreateWorkspacePayload.json");

                given().
                        body(file).
                when().
                    post("/workspaces").
                then().
                        log().all().
                        assertThat().body("workspace.name",equalTo("My change WorkSpaces Using Post Rest Assured"),
                                "workspace.id",matchesPattern("^[a-z0-9-]{36}$"));
    }
}
