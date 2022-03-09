package ru.yandex.mkruchkov.rest_assured;

import helpers.CustomAllureListener;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class BookStoreTest {

    @Test
    void getBooksTest() {

        given()
                .log().uri()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .log().body()
                .body("books.title[0]", is("Git Pocket Guide"));
    }

    @Test
    void generateTokenTest() {

        String data = "{ \"userName\": \"Max\", \"password\": \"1Q2w3e4r!\" }";

         given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().body()
                .statusCode(200)
                .body("token.size()", greaterThan(100));
    }

    @Test
    void generateTokenWithListenerTest() {

        String data = "{ \"userName\": \"Max\", \"password\": \"1Q2w3e4r!\" }";

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().body()
                .statusCode(200)
                .body("token.size()", greaterThan(100));
    }
}
