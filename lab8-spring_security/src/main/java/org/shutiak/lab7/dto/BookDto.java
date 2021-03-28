package org.shutiak.lab7.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    @NotNull
    private String isbn;
    @NotNull
    private String title;
    @NotNull
    private String author;

}
