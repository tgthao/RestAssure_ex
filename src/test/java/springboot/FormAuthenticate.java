package springboot;


import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FormAuthenticate {
    @BeforeClass
    public  void beforeClass(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setBaseUri("https://localhost:8443").build();

    }
    @Test
    public  void form_authenticate_csrf(){
        SessionFilter sessionFilter = new SessionFilter();
        given().
                auth().form("dan", "dan123", new FormAuthConfig("/signin",
                        "txtUsername", "txtPassword").withAutoDetectionOfCsrf())
                .filter(sessionFilter)
                .log().all().
         when().
                get("/login")
        .then().
                log().all()
                .assertThat()
                .statusCode(200);
        System.out.println("Session id: "+sessionFilter.getSessionId());
        given()
                .sessionId(sessionFilter.getSessionId())
                .log().all()
        .when()
                .get("/profile/index")
         .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }
}
