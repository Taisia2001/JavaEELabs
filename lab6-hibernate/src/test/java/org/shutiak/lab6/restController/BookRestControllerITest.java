package org.shutiak.lab6.restController;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.shutiak.lab6.dto.BookDto;
import org.shutiak.lab6.model.Book;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRestControllerITest {

    @LocalServerPort
    void savePort(final int port) {
        RestAssured.port = port;
    }

    @Test
    @Sql("/init.sql")
    @Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldSaveBook(){
        BookDto bookDto = new BookDto("isbn6","bookTitle", "bookAuthor");
        given()
                .body(bookDto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/addBook")
                .then()
                .statusCode(200);
        final Book [] books = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/books?search=isbn6")
                .then()
                .statusCode(200)
                .extract().body().as(Book[].class);
        assertEquals(books[0].getAuthor(),"bookAuthor");
        assertEquals(books[0].getTitle(),"bookTitle");

    }
    @Test
    @Sql("/init.sql")
    @Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldNotSaveBook_isbnExists(){
        //repository.getAllBooks().forEach((b)-> System.out.println(b.toString()));
        given()
                .body(BookRestControllerITest.class.getResourceAsStream("/addBookExists.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/addBook")
                .then()
                .statusCode(400);

    }
    @ParameterizedTest
    @Sql("/init.sql")
    @Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @CsvSource({"sb","ME","mock","jul"})
    void shouldReturnSearchedBooks(final String search){
        final Book [] books = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/books?search="+search)
                .then()
                .statusCode(200)
                .extract().body().as(Book[].class);
        for(Book b:books)
            assertTrue(b.getIsbn().toLowerCase().contains(search.toLowerCase())||b.getTitle().toLowerCase().contains(search.toLowerCase()));

    }
}
