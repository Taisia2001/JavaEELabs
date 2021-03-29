package org.shutiak.lab8.controllers;

import org.shutiak.lab8.exceptions.ConstraintViolationException;
import org.shutiak.lab8.exceptions.IsbnExistsException;
import org.shutiak.lab8.service.BookService;
import org.shutiak.lab8.model.BookEntity;
import org.shutiak.lab8.dto.BookDto;
import org.shutiak.lab8.service.UserService;
import org.shutiak.lab8.utils.TokenUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
public class BookRestController {
    private final BookService bookService;
    private final UserService userService;
    public BookRestController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping(value = "/books")
    public Page<BookEntity> booksList(@RequestParam(name = "search", required = false) String search,
                                      @RequestParam(name = "page", required = false) Integer page,
                                      @RequestParam(name = "size", required = false) Integer size
    ) {
        if(page==null)page=0;
        if(size==null)size=10;
        if(search!=null)
            return bookService.getSearchedBooks(search,page,size);
        return bookService.getAllBooks(page,size);

    }

    @PreAuthorize("hasAuthority('ADD_BOOK')")
    @PostMapping(value = "/addBook")
    public ResponseEntity addNewBook(@Validated @RequestBody final BookDto newBook) {
        try{
            bookService.createNewBook(newBook);
        }catch (IsbnExistsException| ConstraintViolationException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('VIEW_FAVOURITE')")
    @GetMapping(value = "/favourite-books")
    public Set<BookEntity> favourite(final HttpServletRequest httpServletRequest) {
        String token = TokenUtils.getTokenFromRequest(httpServletRequest);
        return userService.getUserByToken(token).getFavouriteBookEntities();
    }

    @PreAuthorize("hasAuthority('DELETE_FROM_FAVOURITE')")
    @DeleteMapping(value = "/unlike/{id}")
    public void unlike(final HttpServletRequest httpServletRequest, @PathVariable int id) {
        String token = TokenUtils.getTokenFromRequest(httpServletRequest);
        userService.unlike(userService.getUserByToken(token),bookService.getBookById(id));
    }


    @PreAuthorize("hasAuthority('ADD_TO_FAVOURITE')")
    @PutMapping(value = "/like/{id}")
    public void like(final HttpServletRequest httpServletRequest, @PathVariable int id) {
        String token = TokenUtils.getTokenFromRequest(httpServletRequest);
        userService.like(userService.getUserByToken(token),bookService.getBookById(id));
    }

}
