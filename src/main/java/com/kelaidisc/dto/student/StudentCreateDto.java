package com.kelaidisc.dto.student;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateDto {

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
  protected LocalDate birthday;

  @NotNull
  protected LocalDate registrationDate;
}
