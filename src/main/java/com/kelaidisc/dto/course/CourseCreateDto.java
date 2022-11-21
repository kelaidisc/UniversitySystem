package com.kelaidisc.dto.course;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateDto {

  @NotEmpty(message = "This field can't be null or empty")
  protected String name;

  @NotEmpty(message = "This field can't be null or empty")
  protected String description;

}

