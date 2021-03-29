package org.shutiak.lab8.controllers;

import org.shutiak.lab8.dto.UserDto;
import org.shutiak.lab8.model.UserEntity;
import org.shutiak.lab8.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
