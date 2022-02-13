package com.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HamCrestAssert58 {
    @Test
    public void hamcrest_asert_on_extracted_response(){
        String name = given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c")
                .when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().
                path("workspaces[0].name");

        System.out.println("workspace name - " +name);
        //System.out.println("workspace name - " +JsonPath.from(res).getString("workspaces[0].name"));
       /* System.out.println("workspace name - " +js.getString("workspaces[0].name"));
        System.out.println("response name: " +res.path("workspaces[0].name"));*/
    }


}
