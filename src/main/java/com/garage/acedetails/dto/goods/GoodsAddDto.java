package com.garage.acedetails.dto.goods;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsAddDto {
  private Long number;

  @NotNull(message = "Goods name mustn't be null")
  @NotBlank(message = "Goods name mustn't be blank")
  private String name;
  private double price;
  private int totalQuantity;
  private String description;
  
  private Long idCategory;
}
