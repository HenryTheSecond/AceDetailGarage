package com.garage.acedetails.dto.goods;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.garage.acedetails.dto.category.CategoryDto;
import com.garage.acedetails.dto.image.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsSimpleInfoDto {
  private Long number;

  @NotNull(message = "Goods name mustn't be null")
  @NotBlank(message = "Goods name mustn't be blank")
  private String name;
  private double price;
  private int totalQuantity;
  private String description;
  
  private CategoryDto category;
  
  private List<ImageDto> listOfImage;
}
