package org.shutiak.lab4.model;

import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder

public class Book {
    private final String isbn;
    private final String title;
    private final String author;
}
