package com.garage.acedetails.util;

import com.garage.acedetails.entity.GoodsCar;

import java.util.Optional;
import java.util.Optional;
import com.garage.acedetails.entity.GoodsCar;

public class GoodsCarValidator {
  public static boolean isValid(GoodsCar goodsCar) {
    return Optional.ofNullable(goodsCar).filter(g -> g.getPrice() > 0).filter(g -> g.getQuantity() >= 0)
        .filter(g -> g.getTotalPrice() > 0).isPresent();
  }
}
