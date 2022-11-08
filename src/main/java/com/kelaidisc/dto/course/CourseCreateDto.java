package com.kelaidisc.dto.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateDto {

  // TODO Add the proper validation annotations
  protected String name;
  protected String description;
}
