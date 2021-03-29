package org.shutiak.lab9.service;

import lombok.RequiredArgsConstructor;
import org.shutiak.lab9.model.BookEntity;
import org.shutiak.lab9.dto.BookDto;
import org.shutiak.lab9.repository.BookRepository;
import org.shutiak.lab9.specifications.BookSpecs;
import org.shutiak.lab9.validation.BookValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @Transactional
    public BookEntity createNewBook(final BookDto newBook) {
        bookValidator.validateNewBook(newBook);
        return bookRepository.save(BookEntity.builder()
                .isbn(newBook.getIsbn())
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .build());
    }

    @Transactional
    public Page<BookEntity> getAllBooks(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page,size, Sort.Direction.ASC,"isbn"));
    }

    @Transactional
    public Page<BookEntity> getSearchedBooks(String search, int page, int size) {
        return bookRepository.findAll(BookSpecs.getBookByIsbnOrTitleOrAuthor("%"+search.toLowerCase()+"%"), PageRequest.of(page, size, Sort.Direction.ASC,"isbn"));
    }

    @Transactional
    public BookEntity getBookByIsbn(String isbn){
        return bookRepository.findBookByIsbn(isbn).orElse(null);
    }

    @Transactional
    public BookEntity getBookById(Integer id){
        return bookRepository.findById(id).orElse(null);
    }
}
