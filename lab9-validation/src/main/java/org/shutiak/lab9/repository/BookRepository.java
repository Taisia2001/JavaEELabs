package org.shutiak.lab9.repository;

import org.shutiak.lab9.model.BookEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer>, JpaSpecificationExecutor<BookEntity> {
    List<BookEntity> findAll();
    Optional<BookEntity> findBookByIsbn(final String isbn);
    boolean existsByIsbn(final String isbn);
}
