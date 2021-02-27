package org.shutiak.lab2.validation;

import lombok.RequiredArgsConstructor;
import org.shutiak.lab2.exceptions.ConstraintViolationException;
import org.shutiak.lab2.exceptions.IsbnExistsException;
import org.shutiak.lab2.model.BookModel;
import org.shutiak.lab2.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository bookRepository;

    public void validateNewBook(final BookModel newBook) {
        validateFields(newBook.getIsbn(),newBook.getTitle(), newBook.getAuthor());
        if (bookRepository.isIsbnExists(newBook.getIsbn())) {
            throw new IsbnExistsException(newBook.getIsbn());
        }


    }


    private void validateFields(final String isbn, final String title, final String author) {
        final List<String> errors = new ArrayList<>();
        if (isbn.trim().length() <=0) {
            errors.add("No isbn!");
        }
        if (title.trim().length() <=0) {
            errors.add("No title!");
        }

        if (author.trim().length() <=0) {
            errors.add("No author!");
        }

        if (!errors.isEmpty()) {
            String message ="";
            for(String error: errors){
                message+=error+" ";
            }
            throw new ConstraintViolationException(errors, message);
        }
    }

}
