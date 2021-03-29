package org.shutiak.lab9.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    @NotEmpty(message = "Isbn can't be empty")
    @Pattern(regexp =  "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$"
            , message = "ISBN has bad format")
    private String isbn;

    @NotEmpty(message = "Can`t create book without title")
    @NotBlank(message = "Can`t create book with blank title")
    private String title;

    @NotEmpty(message = "Can`t create book without author")
    @NotBlank(message = "Can`t create book with blank author")
    @Pattern(regexp = "^\\w+[\\w\\s\\.,-]*$", message = "You can use uppercase and lowercase letters, symbols .,- and space")
    private String author;

}
