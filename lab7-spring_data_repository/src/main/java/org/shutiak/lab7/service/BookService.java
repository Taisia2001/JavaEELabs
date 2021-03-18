package org.shutiak.lab7.service;

import lombok.RequiredArgsConstructor;
import org.shutiak.lab7.model.Book;
import org.shutiak.lab7.dto.BookDto;
import org.shutiak.lab7.repository.BookRepository;
import org.shutiak.lab7.specifications.BookSpecs;
import org.shutiak.lab7.validation.BookValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return bookRepository.save(Book.builder()
                .isbn(newBook.getIsbn())
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .build());
    }

    @Transactional
    public Page<Book> getAllBooks(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page,size, Sort.Direction.ASC,"isbn"));
    }

    @Transactional
    public Page<Book> getSearchedBooks( String search, int page,int size) {
        return bookRepository.findAll(BookSpecs.getBookByIsbnOrTitleOrAuthor("%"+search.toLowerCase()+"%"), PageRequest.of(page, size, Sort.Direction.ASC,"isbn"));
    }

    @Transactional
    public Book getBookByIsbn(String isbn){
        return bookRepository.findBookByIsbn(isbn).orElse(null);
    }
}
