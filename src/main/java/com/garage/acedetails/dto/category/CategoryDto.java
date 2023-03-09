package com.garage.acedetails.dto.category;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
  private Long number;
  
  @Valid
  @NotNull(message = "Category name must not be null")
  @NotBlank(message = "Category name must not be blank")
  private String name;
}
