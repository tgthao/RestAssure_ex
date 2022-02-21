package springboot;


import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
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
    public  void sending_cookie_flow(){
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
                .cookie("JSESSIONID",sessionFilter.getSessionId())
                .log().all()
        .when()
                .get("/profile/index")
         .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }    @Test
    public  void sending_cookie_builder(){
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
        Cookie cookie = new Cookie.Builder("JSESSIONID",sessionFilter
                .getSessionId()).setSecured(true).setHttpOnly(true)
                        .setComment("my cookie"). build();

        given()
                .cookie(cookie)
                .log().all()
        .when()
                .get("/profile/index")
         .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }
}
