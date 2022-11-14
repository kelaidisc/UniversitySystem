package com.kelaidisc.dto.course;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
  @Pattern(regexp = "[A-Z]\\w*", message = "First letter uppercase, rest lowercase")
  protected String name;

  @NotEmpty(message = "This field can't be null or empty")
  protected String description;

}

