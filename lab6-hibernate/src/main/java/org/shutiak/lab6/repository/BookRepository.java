package org.shutiak.lab6.repository;


import lombok.RequiredArgsConstructor;
import org.shutiak.lab6.model.Book;
import org.shutiak.lab6.dto.BookDto;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final EntityManager entityManager;

    public Book saveNewBook(final BookDto newBook) {
        final Book book =  Book.builder()
                .isbn(newBook.getIsbn())
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .build();

        return entityManager.merge(book);

    }

    public List<Book> getAllBooks(){
        return entityManager.createQuery("FROM Book",Book.class)
                .getResultList();
    }
    public Book getBookByIsbn(final String isbn){
        return entityManager.find(Book.class,isbn);
    }

    public boolean isIsbnExists(final String isbn) {

        return entityManager.find(Book.class,isbn)!=null;
    }

    public List<Book> getSearchedBooks(String query) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE LOWER(b.isbn) LIKE :query OR LOWER(b.title) LIKE :query OR LOWER(b.author) LIKE :query"
                ,Book.class)
                .setParameter("query",'%'+query.toLowerCase()+'%').
                        getResultList();
    }
}
