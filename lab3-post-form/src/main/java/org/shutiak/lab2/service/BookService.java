package org.shutiak.lab2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shutiak.lab2.model.Book;
import org.shutiak.lab2.model.BookModel;
import org.shutiak.lab2.repository.BookRepository;
import org.shutiak.lab2.validation.BookValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    public void createNewBook(final BookModel newBook) {
        log.info("Try to create new book: {}", newBook.getIsbn());
        bookValidator.validateNewBook(newBook);
        final Book user = bookRepository.saveNewBook(newBook);
        log.info("New book is created: {}", user);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }
}
