package google.oauth2;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GmailApi {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token="ya29.A0ARrdaM_pwGqKzRRz29u2DpXrDK-BRmf5QDp7UdaQZI1a3dq5cmAexRuWbYkil73eUMBVhY76YmxYWtfoSWehzbqVMS8b5oZiEIzxlhPvbuJDf7ENlsJl-VGBQpxow1nYyYu9IODpyr13rm5IYkiZdlSqhoeVgg";
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://gmail.googleapis.com")
                .addHeader("Authorization","Bearer "+access_token)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }
    @Test
    public void getUserProfile(){
        given(requestSpecification)
                .basePath("/gmail/v1/")
                .pathParam("userid","giangthaoapi@gmail.com")
        .when()
                .get("users/{userid}/profile")
        .then()
                .spec(responseSpecification);
    }
    @Test
    public void sendEmail(){
        String message="From: giangthaoapi@gmail.com\n" +
                "To: thaogiang.90@gmail.com\n" +
                "Subject: RestAssured Test Email\n" +
                "\n" +
                "Send from Automate Rest Assured";
        String base64EncodeMsg = Base64.getUrlEncoder().encodeToString(message.getBytes());
        HashMap<String,String> payload = new HashMap<>();
        payload.put("raw",base64EncodeMsg);
        given(requestSpecification)
                .basePath("/gmail/v1/")
                .pathParam("userid","giangthaoapi@gmail.com")
                .body(payload)
        .when()
                .post("users/{userid}/messages/send")
        .then()
                .spec(responseSpecification);
    }

}
