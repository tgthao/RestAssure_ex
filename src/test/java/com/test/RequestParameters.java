package com.test;

import io.restassured.config.EncoderConfig;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashMap;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class RequestParameters {
    public void single_query_parameters() {
        given().
                baseUri("https://postman-echo.com")
                //.param("foo1","bar1")
                /*Query param metter*/
                .queryParam("foo1", "bar1")
                // .log().all()
                .when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }
    public void multiple_query_parameters() {
        HashMap<String, String> queryHashMap = new HashMap<>();
        queryHashMap.put("foo1", "bar1");
        queryHashMap.put("foo2", "bar2");
        given().
                baseUri("https://postman-echo.com")
                .param("foo1", "bar1", "bar2")
                .param("foo2", "bar1;bar2")
                /*Query param metter*/
                /* .queryParam("foo1","bar1")
                 .queryParam("foo2","bar2")*/
                //.queryParams(queryHashMap)
                .log().all()
                .when().
                    get("/get").
                then().
                    log().all().
                    assertThat().
                    statusCode(200);

    }
    public void multipart_from_data() {
        given().
                baseUri("https://postman-echo.com")
                .multiPart("foo1","bar1")
                .multiPart("foo2","bar2")
                .log().all()
                .when().
                    post("/post/").
                then().
                    log().all().
                    assertThat().
                    statusCode(200);

    }
    public void upload_file_multipart_form_data() {
        String attributes = "{\n" +
                "  \"instructions\": [\n" +
                "    \"GroovyExample.txt\",\n" +
                "    \"Select an item to view its path.\",\n" +
                "    \"Replace 'x' with the name of your variable.\"\n" +
                "  ]\n" +
                "}";
        given().
                baseUri("https://postman-echo.com")
                .multiPart("file",new File("src/main/resources/GroovyExample.txt"))
                .multiPart("attributes",attributes,"application/json")
                .log().all()
                .when().
                    post("/post/").
                then().
                    log().all().
                    assertThat().
                    statusCode(200);

    }

    public void download_file() throws IOException {
 /*       byte[] bytes = given().
                baseUri("https://github.com/appium")
                .when().
                    get("/appium-desktop/releases/download/v1.22.0/Appium-Server-GUI-mac-1.22.0.dmg.blockmap/").
                then().
                    log().all().extract().asByteArray();
        OutputStream os = new FileOutputStream(new File("Appium-Server-GUI-mac-1.22.0.dmg.blockmap"));
        os.write(bytes);
        os.close();*/
        InputStream inputStream = given().
                baseUri("https://github.com/appium")
                .when().
                get("/appium-desktop/releases/download/v1.22.0/Appium-Server-GUI-mac-1.22.0.dmg.blockmap/").
                then().
                log().all().extract().asInputStream();
        OutputStream os = new FileOutputStream(new File("Appium-Server-GUI-mac-1.22.0.dmg.blockmap"));
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        os.write(bytes);
        os.close();
    }    @Test
    public void form_urlencode() throws IOException {
        given().
                baseUri("https://postman-echo.com")
                .config(config().encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .formParam("foo1","bar1")
                .formParam("foo2","bar2")
                .log().all()
        .when()
                .post("/post")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

}
