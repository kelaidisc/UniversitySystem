package com.kelaidisc.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Course extends BaseEntity {

  private String name;
  private String description;

}
