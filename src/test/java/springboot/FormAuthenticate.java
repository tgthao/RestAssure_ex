package springboot;


import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

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
    }
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
    public  void sending_multiple_cookie(){
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
        Cookie cookie1 = new Cookie.Builder("dumy","dumy").build();
        Cookies cookies =new Cookies(cookie,cookie1);
        given()
                //.cookie(cookie)
                .cookies(cookies)
                .log().all()
        .when()
                .get("/profile/index")
         .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }
    public  void fetch_single_cookie(){
        Response response =
        given().log().all()
        .when()
                .get("/profile/index")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();
       // System.out.println(response.xmlPath().getString("html.head.title"));
        System.out.println(response.getCookie("JSESSIONID"));
        System.out.println(response.getDetailedCookie("JSESSIONID"));
        response.getCookie("JSESSIONID");
        response.getDetailedCookie("JSESSIONID");
    }
    @Test
    public  void fetch_multiple_cookie(){
        Response response =
        given().log().all()
        .when()
                .get("/profile/index")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();
       // System.out.println(response.xmlPath().getString("html.head.title"));
        Map<String,String> cookies = response.getCookies();
        for(Map.Entry<String,String> entry: cookies.entrySet()){
            System.out.println("cookie name "+entry.getKey());
            System.out.println("cookie value "+entry.getValue());
        }
        Cookies cookies1 = response.getDetailedCookies();
        List<Cookie> cookieList = cookies1.asList();
        for(Cookie cookie: cookieList){
            System.out.println("cookie "+cookie.toString());
        }
    }
}
