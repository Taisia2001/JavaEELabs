package org.shutiak.lab6.service;

import lombok.RequiredArgsConstructor;
import org.shutiak.lab6.model.Book;
import org.shutiak.lab6.dto.BookDto;
import org.shutiak.lab6.repository.BookRepository;
import org.shutiak.lab6.validation.BookValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @Transactional
    public Book createNewBook(final BookDto newBook) {
        bookValidator.validateNewBook(newBook);
        return bookRepository.saveNewBook(newBook);
    }

    @Transactional
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Transactional
    public List<Book> getSearchedBooks(String search) {
        return bookRepository.getSearchedBooks(search);
    }

    @Transactional
    public Book getBookByIsbn(String isbn){
        return bookRepository.getBookByIsbn(isbn);
    }
}
