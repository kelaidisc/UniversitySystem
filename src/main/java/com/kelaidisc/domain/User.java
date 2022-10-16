package com.kelaidisc.domain;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

  protected String firstName;
  protected String lastName;
  protected String email;
  protected String phone;
  protected LocalDate birthday;

}
