package org.shutiak.lab7.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
