package org.shutiak.lab4.repository;

import lombok.extern.slf4j.Slf4j;
import org.shutiak.lab4.model.Book;
import org.shutiak.lab4.dto.BookDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookRepository {
    private static final Map<String, Book> BOOK_DATABASE = new HashMap<>();;

    public Book saveNewBook(final BookDto newBook) {
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
        return BOOK_DATABASE.values().stream().sorted(Comparator.comparing(Book::getIsbn)).collect(Collectors.toList());
    }

    public boolean isIsbnExists(final String isbn) {
        log.info("Check that book with isbn: {} exists", isbn);

        return BOOK_DATABASE.containsKey(isbn);
    }

    public List<Book> getSearchedBooks(String search) {
        return BOOK_DATABASE.values().
                stream().
                filter(b->b.getIsbn().toLowerCase().contains(search.toLowerCase())||b.getTitle().toLowerCase().contains(search.toLowerCase()))
                .sorted(Comparator.comparing(Book::getIsbn))
                .collect(Collectors.toList());
    }
}
