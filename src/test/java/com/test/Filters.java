package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Filters {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeClass
    public  void beforeClass() throws FileNotFoundException {
        PrintStream fileOutPutStreams = new PrintStream(new File("restAssured.log"));
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter(fileOutPutStreams))
                .addFilter(new ResponseLoggingFilter(fileOutPutStreams));
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecification = responseSpecBuilder.build();
    }
    public void loggingFilter() {
        given().baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.BODY))
                .filter(new ResponseLoggingFilter(LogDetail.STATUS))
                .when()
                .get("/get")
                .then()
                //log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("echoGetSchema.json"));

    }
    public void loggingFilterToFile() throws FileNotFoundException {

/*        given()
                .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.BODY,fileOutPutStream))
                .filter(new ResponseLoggingFilter(LogDetail.BODY,fileOutPutStream))
        .when()
                .get("/get")
                .then()
                //log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("echoGetSchema.json"));*/

    }
    @Test
    public void ReuseloggingFilter(){
        given(requestSpecification)
                .baseUri("https://postman-echo.com")
                .when()
                .get("/get")
                .then()
                .spec(responseSpecification)
                .assertThat().body(matchesJsonSchemaInClasspath("echoGetSchema.json"));
    }
}
