package com.kelaidisc.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class Student extends User {

  private LocalDate registrationDate;

}
