package com.garage.acedetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
  private Long id;
  private Long customerId;
  private double totalPrice;
  private int totalCartDetails;
}
