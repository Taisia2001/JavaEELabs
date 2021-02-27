package org.shutiak.lab2.model;

import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder

public class Book {
    private final String title;
    private final String author;
    private final String isbn;
}
