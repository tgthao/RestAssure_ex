package springboot;


import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FormAuthenticate {
    @BeforeClass
    public  void beforeClass(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setBaseUri("https://localhost:8443").build();

    }
    @Test
    public  void form_authenticate_csrf(){
        given().
                auth().form("dan", "dan123", new FormAuthConfig("/signin",
                        "txtUsername", "txtPassword"))
                        .log().all().
         when().
                get("/login")
        .then().
                log().all()
                .assertThat()
                .statusCode(200);
    }
}
