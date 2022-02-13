package com.test;

import static io.restassured.RestAssured.*;

public class Test {
    @org.testng.annotations.Test
    public void Test(){
                 given().baseUri("https://api.postman.com").
                         header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c")
                .when().
                         get("/workspaces").
                then().
                         log().all().
                        assertThat().statusCode(200);
    }
}
