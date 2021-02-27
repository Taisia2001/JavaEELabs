package org.shutiak.lab2.repository;

import lombok.extern.slf4j.Slf4j;
import org.shutiak.lab2.model.Book;
import org.shutiak.lab2.model.BookModel;
import org.springframework.stereotype.Component;

import java.util.*;
@Slf4j
@Component
public class BookRepository {
    private static final Map<String, Book> BOOK_DATABASE = new HashMap<>();;

    public Book saveNewBook(final BookModel newBook) {
       log.info("Creating new book: {}", newBook);

        final Book book =  Book.builder()
                .isbn(newBook.getIsbn())
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .build();

        BOOK_DATABASE.put(book.getIsbn(), book);
        return book;
    }
    public List<Book> getAllBooks(){
        return new ArrayList(BOOK_DATABASE.values());
    }


    public boolean isIsbnExists(final String isbn) {
        log.info("Check that book with isbn: {} exists", isbn);

        return BOOK_DATABASE.containsKey(isbn);
    }



}
