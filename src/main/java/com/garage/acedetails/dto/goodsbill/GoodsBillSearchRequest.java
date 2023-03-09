package com.garage.acedetails.dto.goodsbill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBillSearchRequest {

  private Long goodsBillId;
  private Long customerId;
  private String fromPurchaseDate;
  private String toPurchaseDate;
  private String orderByProperties;
  private String orderType;
}
