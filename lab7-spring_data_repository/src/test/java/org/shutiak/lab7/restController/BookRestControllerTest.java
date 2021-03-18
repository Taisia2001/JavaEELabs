package org.shutiak.lab7.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.shutiak.lab7.controllers.BookRestController;
import org.shutiak.lab7.dto.BookDto;
import org.shutiak.lab7.exceptions.IsbnExistsException;
import org.shutiak.lab7.model.Book;
import org.shutiak.lab7.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;
    private static final List<Book> books=List.of(
            Book.builder().isbn("isbn1").title("Emma").author("Jane Austen").build(),
            Book.builder().isbn("isbn2").title("The Idiot").author("Fyodor Dostoyevsky").build(),
            Book.builder().isbn("isbn3").title("Game of Thrones").author("George R.R. Martin").build(),
            Book.builder().isbn("isbn4").title("Romeo and Juliet").author("William Shakespeare").build(),
            Book.builder().isbn("isbn5").title("Mockingjay").author("Suzanne Collins").build()
    );

    @ParameterizedTest
    @CsvSource({"sb","ME","mock","jul"})
    @SneakyThrows
    void shouldHandleSearchRequest(final String search){
        List<Book> expectedBooks = books.stream().filter(b->b.getIsbn().toLowerCase().contains(search.toLowerCase())||b.getTitle().toLowerCase().contains(search.toLowerCase())
        ).collect(Collectors.toList());
        when(bookService.getSearchedBooks(search,0,1000)).thenReturn(new PageImpl<>(expectedBooks));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books?search="+search+"&page=0&size=1000")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new PageImpl<>(expectedBooks))));
        verify(bookService,times(1)).getSearchedBooks(search,0,1000);
        verifyNoMoreInteractions(bookService);

    }
    @Test
    @SneakyThrows
    void shouldHandleAllBookRequest(){
        when(bookService.getAllBooks(0,1000)).thenReturn(new PageImpl<>(books));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books?page=0&size=1000")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new PageImpl<>(books))));
        verify(bookService,times(1)).getAllBooks(0,1000);
        verifyNoMoreInteractions(bookService);


    }
    @Test
    public void shouldReturnBadRequest_isbnExists() throws Exception {
        BookDto newBook = new BookDto("isbn", "title", "author");
        when(bookService.createNewBook(any())).thenThrow(new IsbnExistsException(newBook.getIsbn()));
        mockMvc.perform(
                post("/addBook")
                        .content(objectMapper.writeValueAsString(newBook))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Can't add book!!! Isbn isbn has already taken"));
        verify(bookService,times(1)).createNewBook(newBook);
        verifyNoMoreInteractions(bookService);
    }
    @Test
    public void shouldHandleAddRequest_validInput() throws Exception {
        mockMvc.perform(
                post("/addBook")
                .content(objectMapper.writeValueAsString(new BookDto("isbnTest","titleTest","authorTest")))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }


    @ParameterizedTest
    @CsvSource(value={
            ",title,author",
            "isbn,,author",
            "isbn,title,",
            ",,author",
            ",title,",
            "isbn,,",
            ",,",
    })
    @SneakyThrows
    void shouldReturnBadRequest_invalidInput_AddRequest(final String isbn, final String title, final String author){
        mockMvc.perform(
                post("/addBook")
                        .content(objectMapper.writeValueAsString(new BookDto(isbn,title,author)))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
        verify(bookService, never()).createNewBook(any());
    }
}
