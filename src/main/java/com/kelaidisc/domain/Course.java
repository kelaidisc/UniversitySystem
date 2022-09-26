package com.kelaidisc.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Course {

  private Long id;
  private @NonNull String name;
  private String description;

}
