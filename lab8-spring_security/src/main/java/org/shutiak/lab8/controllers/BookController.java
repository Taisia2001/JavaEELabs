package org.shutiak.lab8.controllers;

import org.shutiak.lab8.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping({"/books-list","/"})
    public String books() {
        return "books";
    }
    @GetMapping("/book/{isbn}")
    public String book(Model model, @PathVariable String isbn){
        model.addAttribute("book",bookService.getBookByIsbn(isbn));
        return "book";
    }
    @GetMapping("/favourite")
    public String favourite(){
        return "favourites";
    }
}
