package org.shutiak.lab4.validation;

import lombok.RequiredArgsConstructor;
import org.shutiak.lab4.exceptions.ConstraintViolationException;
import org.shutiak.lab4.exceptions.IsbnExistsException;
import org.shutiak.lab4.dto.BookDto;
import org.shutiak.lab4.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository bookRepository;

    public void validateNewBook(final BookDto newBook) {
        validateFields(newBook.getIsbn(),newBook.getTitle(), newBook.getAuthor());
        if (bookRepository.isIsbnExists(newBook.getIsbn())) {
            throw new IsbnExistsException(newBook.getIsbn());
        }


    }


    private void validateFields(final String isbn, final String title, final String author) {
        final List<String> errors = new ArrayList<>();

        if (isbn==null||isbn.trim().length() <=0) {
            errors.add("No isbn!");
        }
        if (title==null||title.trim().length() <=0) {
            errors.add("No title!");
        }

        if (author==null||author.trim().length() <=0) {
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
