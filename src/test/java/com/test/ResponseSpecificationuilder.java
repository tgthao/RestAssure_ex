package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class ResponseSpecificationuilder {
    @BeforeClass
    public void beforeCLass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-API-KEY", "PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c");
        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    /*RequestSpecification requestSpecification =  with().
            baseUri("https://api.postman.com").
            header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c").
            log().all();*/

    @Test
    public void validate_status_code(){
        get("/workspaces").then().log().all();

    }
    @Test
    public void validate_response_body(){
        Response response = get("/workspaces").then().
                extract().response();
        assertThat(response.path("workspaces[0].name").toString(),is(equalTo("My Workspace")));

    }
}
