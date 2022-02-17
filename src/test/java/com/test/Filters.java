package com.test;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Filters {
    @Test
    public void loggingFilter() {
        given().baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.BODY))
                .filter(new ResponseLoggingFilter(LogDetail.STATUS))
                .when()
                .get("/get")
                .then()
                //log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("echoGetSchema.json"));

    }    @Test
    public void loggingFilterToFile() throws FileNotFoundException {
        PrintStream fileOutPutStream = new PrintStream(new File("restAssured.log"));

        given()
                .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.BODY,fileOutPutStream))
                .filter(new ResponseLoggingFilter(LogDetail.BODY,fileOutPutStream))
        .when()
                .get("/get")
                .then()
                //log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("echoGetSchema.json"));

    }
}
