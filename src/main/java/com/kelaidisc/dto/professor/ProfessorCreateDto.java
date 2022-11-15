package com.kelaidisc.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorCreateDto {

    @NotEmpty(message = "This field can't be null or empty")
    @Pattern(regexp = "[A-Z]\\w*", message = "First letter uppercase, rest lowercase")
    protected String lastName;

    @NotEmpty(message = "This field can't be null or empty")
    @Pattern(regexp = "[A-Z]\\w*", message = "First letter uppercase, rest lowercase")
    protected String firstName;

    @Email
    protected String email;

    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$")
    protected String phone;

    @NotNull
    @Past
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    protected LocalDate birthday;
}
