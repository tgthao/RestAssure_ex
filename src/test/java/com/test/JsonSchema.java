package com.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchema {
    @Test
    public void jsonSchemaValidation() {
        given().baseUri("https://postman-echo.com")
                .when().get("/get")
                .then().
                log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("echoGetSchema.json"));

    }
}
