package com.garage.acedetails.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import com.garage.acedetails.entity.GoodBillDetailsPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodBillDetailsDTO {
  @NotNull
  private GoodBillDetailsPK goodBillDetailsPK;

  @DecimalMin(value = "0", inclusive = false)
  private int quantity;

  @DecimalMin(value = "0", inclusive = false)
  private double price;

  @DecimalMin(value = "0", inclusive = false)
  private double totalPrice;

}
