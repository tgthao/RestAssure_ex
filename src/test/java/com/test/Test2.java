package com.test;

import static io.restassured.RestAssured.given;

public class Test2 {
    @org.testng.annotations.Test
    public void Test1(){
        given().baseUri("https://flirb2cprod.b2clogin.com/flirb2cprod.onmicrosoft.com/oauth2/v2.0/token").
                header("p","B2C_1A_RayMarineROPC").
                header("response_type","token%20id_token").
                header("grant_type","password").
                header("client_id","6ad1fda7-90bb-4570-b2fb-cdc83f617964").
                header("scope","openid%206ad1fda7-90bb-4570-b2fb-cdc83f617964%20offline_access").
                header("username","sieutt%40nexlesoft.com").
                header("password","Sieu123%40")
                .when().post().
                then().
                log().all().
                assertThat().statusCode(200);
    }
}
