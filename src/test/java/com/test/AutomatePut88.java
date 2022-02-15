package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;


public class AutomatePut88 {
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
    public void validate_put_request_bdd_style(){
        String workspaceId = "f58cf130-0050-4698-87b4-e49e2d9922e0";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "            \"name\":\"UAT API TestFlir WorkSpaces\",\n" +
                "            \"type\":\"team\",\n" +
                "            \"description\":\"Edit by TGT\"\n" +
                "    }\n" +
                "    \n" +
                "}";
        given().
                body(payload).
                pathParam("workspaceId",workspaceId).
        when().
                put("/workspaces/{workspaceId}").
        then().
                log().all().
                assertThat().
                body("workspace.name",equalTo("UAT API TestFlir WorkSpaces"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id",equalTo(workspaceId));
    }
    @Test
    public void validate_delete_request_bdd_style(){
        String workspaceId = "473228e8-6608-4ad3-b51f-42d830fae7fc";
        given().
                pathParam("workspaceId",workspaceId).
        when().
                delete("/workspaces/{workspaceId}").
        then().
                log().all().
                assertThat().
                body("workspace.id",matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id",equalTo(workspaceId));
    }
}
