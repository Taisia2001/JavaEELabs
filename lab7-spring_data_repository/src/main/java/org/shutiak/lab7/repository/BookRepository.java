package org.shutiak.lab7.repository;

import org.shutiak.lab7.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
    List<Book> findAll();
    Optional<Book> findBookByIsbn(final String isbn);
    boolean existsByIsbn(final String isbn);
}
