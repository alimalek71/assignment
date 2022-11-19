package ir.alimalek.assignment.controller;

import io.restassured.RestAssured;
import ir.alimalek.assignment.repository.InformationRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.io.File;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InformationControllerTest {

    @LocalServerPort
    int port;
    @Autowired
    private InformationRepository repository;

    @BeforeEach
    @AfterEach
    void cleanUp() {
        RestAssured.port = port;
        repository.deleteAll();
    }

    @Test
    void givenValidCSVFile_whenUploadIt_thenGetHttpStatusCreated() {
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("./src/test/resources/exercise.csv"), "text/csv")
                .when()
                .post("/api/v1/information")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void givenInvalidCSVFile_whenUploadIt_thenGetBadRequest() {
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("./src/test/resources/bad-csv.csv"), "text/csv")
                .when()
                .post("/api/v1/information")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenNonCSVFile_whenUploadIt_thenGetBadRequest() {
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("./src/test/resources/empty.txt"), "text/text")
                .when()
                .post("/api/v1/information")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenUploadedInfo_whenFetchInfoPageByPage_thenGetPage() {
        // Uploaded data
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("./src/test/resources/exercise.csv"), "text/csv")
                .when()
                .post("/api/v1/information")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

        given()
                .param("page", 0)
                .param("size", 10)
                .when()
                .get("/api/v1/information")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("content", Matchers.hasSize(10))
                .body("size", Matchers.is(10))
                .body("totalElements", Matchers.is(18))
                .body("totalPages", Matchers.is(2));
    }

    @Test
    void givenUploadedInfo_whenFetchInfoByCode_thenGetInformation() {
        // Uploaded data
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("./src/test/resources/exercise.csv"), "text/csv")
                .when()
                .post("/api/v1/information")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

        given()
                .param("page", 0)
                .param("size", 10)
                .when()
                .get("/api/v1/information/" + "Type 7")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.notNullValue())
                .body("source", Matchers.is("ZIB"))
                .body("codeListCode", Matchers.is("ZIB002"))
                .body("code", Matchers.is("Type 7"))
                .body("displayValue", Matchers.is("Helemaal vloeibaar"))
                .body("longDescription", Matchers.is(""))
                .body("fromDate", Matchers.is("2019-01-01"))
                .body("toDate", Matchers.nullValue())
                .body("sortingPriority", Matchers.nullValue());
    }

    @Test
    void givenUploadedInfo_whenFetchInfoByNonExistingCode_thenGetHttpNotFound() {
        // Uploaded data
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("./src/test/resources/exercise.csv"), "text/csv")
                .when()
                .post("/api/v1/information")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

        given()
                .param("page", 0)
                .param("size", 10)
                .when()
                .get("/api/v1/information/" + "Type 700")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenUploadedInfo_whenDeleteAll_thenFetchInfoReturnEmptyList() {
        // Uploaded data
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("./src/test/resources/exercise.csv"), "text/csv")
                .when()
                .post("/api/v1/information")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

        Assertions.assertEquals(18, repository.count());

        given()
                .param("page", 0)
                .param("size", 10)
                .when()
                .delete("/api/v1/information/all")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Assertions.assertEquals(0, repository.count());

        given()
                .param("page", 0)
                .param("size", 10)
                .when()
                .get("/api/v1/information")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("content", Matchers.hasSize(0))
                .body("totalElements", Matchers.is(0))
                .body("totalPages", Matchers.is(0));
    }

}