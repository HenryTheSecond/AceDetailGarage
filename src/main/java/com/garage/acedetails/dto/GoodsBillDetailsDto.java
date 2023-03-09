package com.garage.acedetails.dto;

import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBillDetailsDto {
  private Long goodsBillId;
  @ExcelColumnIndex(index = 1)
  private Long goodsId;
  @ExcelColumnIndex(index = 2)
  private int quantity;
}
