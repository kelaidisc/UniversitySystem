package com.kelaidisc.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

  private LocalDate registrationDate;

}
