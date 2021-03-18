package org.shutiak.lab7.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @NotBlank
    @Column(name="isbn", nullable = false,unique = true)
    private  String isbn;

    @NotBlank
    @Column(name="title",nullable = false)
    private  String title;

    @NotBlank
    @Column(name="author",nullable = false)
    private  String author;
}
