package com.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HamCrestAssert59 {
    @Test
    public void validate_response_body_hamcrest_learnings(){
         given().baseUri("https://api.postman.com").
                header("X-API-KEY","PMAK-6207aa1f91e45e028abb0eea-96be721c435eb5d9df2049fffe94a58c3c")
                .when().
                get("/workspaces").
                then().
            //    log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name"
                ,containsInAnyOrder("My Workspace", "NexleSoft", "Test Flir", "Rest Assure", "Team Workspace"),
                        "workspaces.name",hasSize(5),
                        "workspaces[0]",hasKey("id"),
                        "workspaces[0].name",allOf(startsWith("My"),containsString("Work")));
    }


}
