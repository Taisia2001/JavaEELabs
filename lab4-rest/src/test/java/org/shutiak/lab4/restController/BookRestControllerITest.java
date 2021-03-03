package org.shutiak.lab4.restController;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.shutiak.lab4.dto.BookDto;
import org.shutiak.lab4.model.Book;
import org.shutiak.lab4.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRestControllerITest {
    @Autowired
    private BookRepository repository;
    @LocalServerPort
    void savePort(final int port) {
        RestAssured.port = port;
    }

    @BeforeEach
    void createBooks(){
        repository.saveNewBook(new BookDto("isbn1","Emma","Jane Austen"));
        repository.saveNewBook(new BookDto("isbn2","The Idiot","Fyodor Dostoyevsky"));
        repository.saveNewBook(new BookDto("isbn3","Game of Thrones","George R.R. Martin"));
        repository.saveNewBook(new BookDto("isbn4","Romeo and Juliet","William Shakespeare"));
        repository.saveNewBook(new BookDto("isbn5","Mockingjay","Suzanne Collins"));
    }
    @Test
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
    void shouldNotSaveBook_isbnExists(){
        given()
                .body(BookRestControllerITest.class.getResourceAsStream("/addBookExists.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/addBook")
                .then()
                .statusCode(400);

    }
    @ParameterizedTest
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
