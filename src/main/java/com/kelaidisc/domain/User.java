package com.kelaidisc.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User {

  protected Long id;
  protected String firstName;
  protected String lastName;
  protected String email;
  protected String phone;
  protected LocalDate birthday;

}
