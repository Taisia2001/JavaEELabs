package org.shutiak.lab9.exceptions;

public class IsbnCheckSumException extends IsbnException {

    public IsbnCheckSumException(final String isbn, int expectedSum, int sum) {
        super("Can't add book!!! Isbn "+isbn+" has wrong sum, expected sum: "+expectedSum+", counted sum: "+sum);
    }

}