package com.kelaidisc.dto.course;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CourseUpdateDto extends CourseCreateDto {

  /* question:
   to use validation groups we could delete this class
   and add specific behavior for each case in the superclass
   by defining 2 marker interfaces and then adding a service
   with @Validated methods?

   answer:
   yes. We will define 2 marker interfaces, use @Validated and specify when validation annotations should be used depending on which group
   */

  @NotNull
  @Positive
  private Long id;

}
