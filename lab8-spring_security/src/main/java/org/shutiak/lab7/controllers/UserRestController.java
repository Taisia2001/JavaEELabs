package org.shutiak.lab7.controllers;

import lombok.SneakyThrows;
import org.shutiak.lab7.dto.UserDto;
import org.shutiak.lab7.exceptions.ConstraintViolationException;
import org.shutiak.lab7.exceptions.IsbnExistsException;
import org.shutiak.lab7.model.UserEntity;
import org.shutiak.lab7.service.BookService;
import org.shutiak.lab7.model.BookEntity;
import org.shutiak.lab7.dto.BookDto;
import org.shutiak.lab7.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {
    private final UserService userService;
    public UserRestController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<String>  addNewUser(@RequestBody final UserDto newUser ){
        if(!userService.existsByLogin(newUser.getLogin())){
            HttpHeaders headers  = new HttpHeaders();
            UserEntity userEntity = userService.createNewUser(newUser);
            UserDetails userDetails = userService.loadUserByUsername(newUser.getLogin());
            headers.set("Authorization", userService.generateToken(userEntity.getLogin(), userDetails.getAuthorities()));
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(userEntity.toString());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Login exists");
    }

}
