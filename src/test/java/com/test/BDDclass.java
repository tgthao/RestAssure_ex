package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;


public class BDDclass {
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
        String payload = "{\n" +
                "    \"workspace\":{\n" +
                "        \"name\":\"testingWorkspace\",\n" +
                "        \"type\":\"personal\",\n" +


                "        \"description\":\"Rest Assured created this\"\n" +
                "    }\n" +
                "}";
        Response response = with().
                body(payload).
                post("/workspaces");
        assertThat(response.<String>path("workspace.name"), equalTo("testingWorkspace"));
               /* given().
                        body(payload).
                when().
                    post("/workspaces").
                then().assertThat().body("workspace.name",equalTo("testingWorkspace"),
                                "workspace.id",matchesPattern("473228e8-6608-4ad3-b51f-42d830fae7fc"));*/
    }

@Test
    public void validate_post_request_non_bdd_styple(){
        String payload = "{\n" +
                "    \"workspace\":{\n" +
                "        \"name\":\"testingWorkspace\",\n" +
                "        \"type\":\"personal\",\n" +


                "        \"description\":\"Rest Assured created this\"\n" +
                "    }\n" +
                "}";
        Response response = with().
                body(payload).
                post("/workspaces");
        assertThat(response.<String>path("workspace.name"), equalTo("testingWorkspace"));
               /* given().
                        body(payload).
                when().
                    post("/workspaces").
                then().assertThat().body("workspace.name",equalTo("testingWorkspace"),
                                "workspace.id",matchesPattern("473228e8-6608-4ad3-b51f-42d830fae7fc"));*/
    }
}
