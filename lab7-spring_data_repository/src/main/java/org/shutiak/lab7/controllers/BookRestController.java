package org.shutiak.lab7.controllers;

import org.shutiak.lab7.exceptions.ConstraintViolationException;
import org.shutiak.lab7.exceptions.IsbnExistsException;
import org.shutiak.lab7.service.BookService;
import org.shutiak.lab7.model.Book;
import org.shutiak.lab7.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookRestController {
    private final BookService service;
    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping(value = "/books")
    public Page<Book> booksList(@RequestParam(name = "search", required = false) String search,
                                @RequestParam(name = "page", required = false) Integer page,
                                @RequestParam(name = "size", required = false) Integer size
    ) {
        if(page==null)page=0;
        if(size==null)size=10;
        if(search!=null)
            return service.getSearchedBooks(search,page,size);
        return service.getAllBooks(page,size);

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
