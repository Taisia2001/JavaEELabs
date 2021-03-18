package org.shutiak.lab6.model;

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
    @Column(nullable = false,unique = true)
    private  String isbn;

    @NotBlank
    @Column(nullable = false)
    private  String title;

    @NotBlank
    @Column(nullable = false)
    private  String author;
}
