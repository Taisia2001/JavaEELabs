package org.shutiak.lab2.controllers;

import org.shutiak.lab2.model.Book;
import org.shutiak.lab2.model.BookModel;
import org.shutiak.lab2.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
public class BookController {
    private final BookService service;


    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping({"/books-list","/"})
    public String booksList(Model model) {
        List<Book> books = service.getAllBooks();
        model.addAttribute("books", books);
        return "books-list";
    }
    @GetMapping(value = "/add-book")
    public String addNewBookForm() {
        return "add-book";
    }

    @PostMapping(value = "/add-book")
    public String addNewBook(@ModelAttribute("book") BookModel newBook, RedirectAttributes redirAttrs) {
        try{
            service.createNewBook(newBook);
        }catch (Exception e){
            redirAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books-list";
    }


    }
