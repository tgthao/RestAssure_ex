package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simple.SimplePojo;
import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class WorkspacePojoTest {
    ResponseSpecification customResponeSpecification;
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com")
                .addHeader("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c")

        // addHeader("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c").
                /*setConfig(config.encoderConfig(EncoderConfig.encoderConfig().
                        appendDefaultContentCharsetToContentTypeIfUndefined(false))).*/
        .setContentType("application/json; charset=utf-8")
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponeSpecification = responseSpecBuilder.build();
    }
    public void workspace_data_pojo() throws JsonProcessingException {
        Workspace workspace = new Workspace("My API RestAssured 1","personal","API RestAssured created");
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);
        WorkspaceRoot deserializedRoot = given().body(workspaceRoot)
                .when().post("/workspaces")
                .then().spec(customResponeSpecification)
                .extract()
                .response()
                .as(WorkspaceRoot.class);
        assertThat(deserializedRoot.getWorkspace().getName(),equalTo(workspaceRoot.getWorkspace().getName()));
        assertThat(deserializedRoot.getWorkspace().getId(),matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test(dataProvider = "workspace")
    public void workspace_data_provider(String name, String type, String description) {
        Workspace workspace = new Workspace(name,type,description);
        HashMap<String,String> myHashmap = new HashMap<>();
        workspace.setHashMap(myHashmap);
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);
        WorkspaceRoot deserializedRoot = given().body(workspaceRoot)
                .when().post("/workspaces")
                .then().spec(customResponeSpecification)
                .extract()
                .response()
                .as(WorkspaceRoot.class);
        assertThat(deserializedRoot.getWorkspace().getName(),equalTo(workspaceRoot.getWorkspace().getName()));
        assertThat(deserializedRoot.getWorkspace().getId(),matchesPattern("^[a-z0-9-]{36}$"));
    }
    @DataProvider(name = "workspace")
    public Object[][] getWorkspace(){
        return new Object[][]{
                {"my Workspace API Rest Assured 01", "personal", "descr˚iption"},
                {"my Workspace API Rest Assured 02", "personal", "description"}
        };
    }
}
