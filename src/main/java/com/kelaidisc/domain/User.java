package com.kelaidisc.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User {



  protected Long id;
  protected @NonNull  String firstName;
  protected @NonNull String lastName;
  protected String email;
  protected String phone;
  protected LocalDate birthday;

}
