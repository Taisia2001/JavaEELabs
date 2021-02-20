package org.shutiak.service;

import org.junit.jupiter.api.Test;
import org.shutiak.exceptions.LoginExistsException;
import org.shutiak.exceptions.UserNotFoundException;
import org.shutiak.model.NewUser;
import org.shutiak.model.User;
import org.shutiak.repository.UserRepository;
import org.shutiak.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @SpyBean
    private UserRepository userRepository;
    @SpyBean
    private UserValidator userValidator;


    @Test
    void shouldCreateValidUser_userValidInput(){
        NewUser newUser = NewUser.builder().login("login").password("pass").fullName("name").build();
        userService.createNewUser(newUser);

        verify(userValidator).validateNewUser(newUser);
        verify(userRepository).saveNewUser(newUser);
        verify(userRepository).isLoginExists(newUser.getLogin());

        assertThat(userRepository.getUserByLogin(newUser.getLogin()))
                .returns(newUser.getLogin(),User::getLogin)
                .returns(newUser.getPassword(), User::getPassword)
                .returns(newUser.getFullName(), User::getFullName);

       }

    @Test
    void shouldThrowException_whenLoginExists(){
        NewUser newUser = NewUser.builder().login("loginException").password("pass").fullName("name").build();
        userService.createNewUser(newUser);

        assertThrows(LoginExistsException.class, ()-> userService.createNewUser(newUser));
    }


    @Test
    void shouldReturnUser_loginExists(){
        NewUser newUser = NewUser.builder().login("loginExists").password("pass").fullName("name").build();
        userService.createNewUser(newUser);

        assertThat(
                userService.getUserByLogin(newUser.getLogin()))
                .isNotNull()
                .isEqualToComparingFieldByField(newUser);
    }

    @Test
    void shouldThrowExceptionReturnUser_loginNotExists(){
        assertThrows(UserNotFoundException.class, ()-> userService.getUserByLogin("loginNotExist"));

    }
}
