package org.shutiak.lab4.exceptions;

public class IsbnExistsException extends RuntimeException {

    public IsbnExistsException(final String isbn) {
        super(String.format("Can't add book!!! Isbn %s has already taken", isbn));
    }

}