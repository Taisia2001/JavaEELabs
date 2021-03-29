package org.shutiak.lab9.exceptions;

public class IsbnExistsException extends IsbnException {

    public IsbnExistsException(final String isbn) {
        super("Can't add book!!! Isbn "+isbn+" has already taken");
    }

}