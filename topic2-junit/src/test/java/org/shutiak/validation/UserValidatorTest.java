package org.shutiak.validation;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.shutiak.exceptions.ConstraintViolationException;
import org.shutiak.exceptions.LoginExistsException;
import org.shutiak.model.NewUser;
import org.shutiak.repository.UserRepository;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserValidator userValidator;


    @Test
    void shouldPassValidation() {
        userValidator.validateNewUser(
                NewUser.builder()
                        .login("login")
                        .password("pass")
                        .fullName("name")
                        .build()
        );

        verify(userRepository).isLoginExists("login");
    }

    @Test
    void shouldThrowException_whenLoginExists(){
        when(userRepository.isLoginExists("login")).thenReturn(true);
        assertThatThrownBy(()->
        userValidator.validateNewUser(
                NewUser.builder()
                        .login("login")
                        .password("pass")
                        .fullName("name")
                        .build()
        )).isInstanceOf(LoginExistsException.class)
        .hasMessage("Login login already taken");
    }

    @ParameterizedTest
    @MethodSource("passwordTestData")
    void shouldThrowException_whenPasswordIsInvalid(final String password, final List<String> expectedErrors){
        assertThatThrownBy(()->
                userValidator.validateNewUser(
                        NewUser.builder()
                                .login("login")
                                .password(password)
                                .fullName("name")
                                .build()
                )).isInstanceOfSatisfying(ConstraintViolationException.class,ex ->{
                    assertThat(ex.getErrors())
                            .containsExactlyInAnyOrderElementsOf(expectedErrors);
        })
                .hasMessage("You have errors in you object");
    }



    private static Stream<Arguments> passwordTestData(){
        return Stream.of(
                Arguments.of("pa", List.of("Password has invalid size") ),
                Arguments.of("papapapapapapap", List.of("Password has invalid size") ),
                Arguments.of("pa*/=", List.of("Password doesn't match regex") ),
                Arguments.of("/=", List.of("Password doesn't match regex","Password has invalid size") )
        );
    }

}
