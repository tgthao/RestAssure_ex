package com.test;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;

public class RequestSpecificationExample {
    RequestSpecification requestSpecification =  with().
            baseUri("https://api.postman.com").
            header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c");

    @Test
    public void validate_status_code(){
                given().spec(requestSpecification).
                when().
                    get("/workspaces").
                then().
                    log().all().
                    assertThat().
                    statusCode(200)
                ;
    }
    @Test
    public void validate_response_body(){
        given().spec(requestSpecification).
                when().
                    get("/workspaces").
                then().
                    log().all().
                    assertThat().
                    statusCode(200).
                    body("workspaces[0].name",equalTo("My Workspace"))
                ;

    }
}
