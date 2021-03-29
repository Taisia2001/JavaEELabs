package org.shutiak.lab9.validation;

import lombok.RequiredArgsConstructor;
import org.shutiak.lab9.exceptions.ConstraintViolationException;
import org.shutiak.lab9.exceptions.IsbnCheckSumException;
import org.shutiak.lab9.exceptions.IsbnExistsException;
import org.shutiak.lab9.dto.BookDto;
import org.shutiak.lab9.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository bookRepository;

    public void validateNewBook(final BookDto newBook) {
        if (bookRepository.existsByIsbn(newBook.getIsbn())) {
            throw new IsbnExistsException(newBook.getIsbn());
        }
        validateIsbnSum(newBook.getIsbn());
    }


    private void validateIsbnSum(final String isbn) {
        final List<String> errors = new ArrayList<>();
        String tempDigits = isbn.replaceAll("\\D","");
        int expectedSum = Character.getNumericValue(tempDigits.charAt(tempDigits.length()-1));
        int sum = 0;
        for(int i = 9, index = tempDigits.length()-2;i>0;i--, index--){
            sum += i*Character.getNumericValue(tempDigits.charAt(index));
        }
        if(sum%11!=expectedSum)
            throw new IsbnCheckSumException(isbn,expectedSum, sum%11);

    }

}
