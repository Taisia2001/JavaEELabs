package org.shutiak.lab4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shutiak.lab4.model.Book;
import org.shutiak.lab4.dto.BookDto;
import org.shutiak.lab4.repository.BookRepository;
import org.shutiak.lab4.validation.BookValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    public boolean createNewBook(final BookDto newBook) {
        log.info("Try to create new book: {}", newBook.getIsbn());
        bookValidator.validateNewBook(newBook);
        final Book book = bookRepository.saveNewBook(newBook);
        log.info("New book is created: {}", book);
        return true;
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public List<Book> getSearchedBooks(String search) {
        return bookRepository.getSearchedBooks(search);
    }
}
