package com.kelaidisc.dto.course;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CourseUpdateDto extends CourseCreateDto {

  // TODO Add the proper validation annotations
  /* question:
   to use validation groups we could delete this class
   and add specific behavior for each case in the superclass
   by defining 2 marker interfaces and then adding a service
   with @Validated methods?
   */

  @NotNull
  @Positive
  @Max(3)
  private Long id;

}
