package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;


public class JacksonAPI_JSONObject {
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
    @Test
    public void validate_post_request_payload_as_map() throws JsonProcessingException {
        HashMap<String,Object> mainObject = new HashMap<String,Object>();
        HashMap<String,String> nestedObjects = new HashMap<String,String>();
        nestedObjects.put("name","My Name API UAT");
        nestedObjects.put("type","personal");
        nestedObjects.put("description","My Name API UAT created by GT");
        mainObject.put("workspace",nestedObjects);

        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectStr = objectMapper.writeValueAsString(mainObject);
                given().
                        body(mainObjectStr).
                when().
                    post("/workspaces").
                then().
                        log().all().
                        assertThat().body("workspace.name",equalTo("My change WorkSpaces Using Post Rest Assured"),
                                "workspace.id",matchesPattern("^[a-z0-9-]{36}$"));
    }
}
