package com.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class RequestBuilder {
    RequestSpecification requestSpecification;
    @BeforeClass
    public void beforeCLass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c");
        requestSpecBuilder.log(LogDetail.HEADERS);
        requestSpecification = requestSpecBuilder.build();

    }
    /*RequestSpecification requestSpecification =  with().
            baseUri("https://api.postman.com").
            header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c").
            log().all();*/

    @Test
    public void validate_status_code(){
        Response response = given(requestSpecification).
                header("dummyHeader","dummyHeader").
                get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));

    }
    @Test
    public void validate_response_body(){
        Response response = given(requestSpecification).get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));
        assertThat(response.path("workspaces[0].name").toString(),is(equalTo("My Workspace")));

    }
}
