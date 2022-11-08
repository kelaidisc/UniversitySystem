package com.kelaidisc.dto.course;

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

  // TODO Add the proper validation annotations
  private Long id;

}
