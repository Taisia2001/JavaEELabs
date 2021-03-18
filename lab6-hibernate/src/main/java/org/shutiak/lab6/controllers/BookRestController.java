package org.shutiak.lab6.controllers;

import org.shutiak.lab6.exceptions.ConstraintViolationException;
import org.shutiak.lab6.exceptions.IsbnExistsException;
import org.shutiak.lab6.service.BookService;
import org.shutiak.lab6.model.Book;
import org.shutiak.lab6.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookRestController {
    private final BookService service;
    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping(value = "/books")
    public List<Book> booksList(@RequestParam(name = "search", required = false) String search) {
        if(search!=null)
            return service.getSearchedBooks(search);
        return service.getAllBooks();
    }

    @PostMapping(value = "/addBook")
    public ResponseEntity addNewBook(@Validated @RequestBody final BookDto newBook) {
        try{
            service.createNewBook(newBook);
        }catch (IsbnExistsException| ConstraintViolationException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
