package com.kelaidisc.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollDto {

  @NotEmpty
  protected Set<Long> ids;
}
