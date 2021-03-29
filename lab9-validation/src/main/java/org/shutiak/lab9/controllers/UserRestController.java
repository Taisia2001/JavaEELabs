package org.shutiak.lab9.controllers;

import org.shutiak.lab9.dto.UserDto;
import org.shutiak.lab9.model.UserEntity;
import org.shutiak.lab9.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserRestController {
    private final UserService userService;
    public UserRestController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<String>  addNewUser(@Valid @RequestBody final UserDto newUser ){
        if(!userService.existsByLogin(newUser.getLogin())){
            HttpHeaders headers  = new HttpHeaders();
            UserEntity userEntity = userService.createNewUser(newUser);
            UserDetails userDetails = userService.loadUserByUsername(newUser.getLogin());
            headers.set("Authorization", userService.generateToken(userEntity.getLogin(), userDetails.getAuthorities()));
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new UserDto(userEntity.getLogin(),userEntity.getPassword()).toString());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Login exists");
    }

}
