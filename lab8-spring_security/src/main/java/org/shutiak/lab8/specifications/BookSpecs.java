package org.shutiak.lab8.specifications;

import org.shutiak.lab8.model.BookEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class BookSpecs {
    public static Specification<BookEntity> getBookByIsbnOrTitleOrAuthor(String search) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicateForIsbn = criteriaBuilder.like(criteriaBuilder.lower(root.get("isbn")), "%"+search.toLowerCase()+"%");
            Predicate predicateForTitle = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%"+search.toLowerCase()+"%");
            Predicate predicateForAuthor = criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%"+search.toLowerCase()+"%");
            return criteriaBuilder.or(predicateForIsbn,predicateForTitle,predicateForAuthor);
        };

    }

}
