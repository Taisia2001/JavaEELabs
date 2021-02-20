package org.shutiak.service;

import org.shutiak.repository.UserRepository;
import org.shutiak.validation.UserValidator;
import org.springframework.stereotype.Service;

import org.shutiak.model.NewUser;
import org.shutiak.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public void createNewUser(final NewUser newUser) {
        log.info("Try to create new user: {}", newUser.getLogin());
        userValidator.validateNewUser(newUser);
        final User user = userRepository.saveNewUser(newUser);
        log.info("New user is created: {}", user);
    }

    public User getUserByLogin(final String login) {
        log.info("Try to get user by login: {}", login);
        return userRepository.getUserByLogin(login);
    }

}
