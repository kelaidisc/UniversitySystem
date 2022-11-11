package com.kelaidisc.dto.course;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateDto {

  // TODO Add the proper validation annotations

  @NotEmpty(message = "This field can't be null or empty")
  @Pattern(regexp = "[A-Z]\\w*", message = "First letter uppercase,rest lowercase")
  protected String name;

  @NotEmpty(message = "This field can't be null or empty")
  protected String description;

}

