package org.shutiak.lab9.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty(message = "Login can't be empty")
    @Pattern(regexp = "^[A-Za-z\\d]+$", message = "Login can contain latin digits and letters only")
    private String login;

    @NotEmpty(message = "Password can't be empty")
    @NotBlank(message = "Password can`t be blank")
    @Size(max = 20, min = 8, message = "Password should contain from 8 to 20 symbols")
    private String password;
}
