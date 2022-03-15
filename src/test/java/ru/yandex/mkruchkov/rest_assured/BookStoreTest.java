package ru.yandex.mkruchkov.rest_assured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class BookStoreTest {
    String data = "{ \"userName\": \"Max\", \"password\": \"1Q2w3e4r!\" }";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
    }

    @Test
    void getBooksTest() {

        given()
                .log().uri()
                .get("/BookStore/v1/Books")
                .then()
                .body(matchesJsonSchemaInClasspath("JSONschema/getBooks.json"))
                .statusCode(200)
                .log().body()
                .body("books.title[0]", is("Git Pocket Guide"));
    }

    @Test
    void generateTokenTest() {

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .body(matchesJsonSchemaInClasspath("JSONschema/generateTokenWithListener.json"))
                .log().body()
                .statusCode(200)
                .body("token.size()", greaterThan(100));
    }

    @Test
    void generateTokenWithListenerTest() {

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(data)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .body(matchesJsonSchemaInClasspath("JSONschema/generateTokenWithListener.json"))
                .log().body()
                .statusCode(200)
                .body("token.size()", greaterThan(100));
    }
}
