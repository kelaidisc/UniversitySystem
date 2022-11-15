package com.kelaidisc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDto {

  // TODO This is missing validation annotations ok
  @NotEmpty
  private Set<Long> ids;

}
