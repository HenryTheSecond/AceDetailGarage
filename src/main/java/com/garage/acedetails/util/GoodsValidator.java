package com.garage.acedetails.util;


import java.util.Optional;
import com.garage.acedetails.entity.Goods;

public class GoodsValidator {
  public static boolean isValid(Goods goods) {
    return Optional.ofNullable(goods).filter(g -> g.getName() != null).filter(g -> g.getPrice() > 0)
        .filter(g -> g.getTotalQuantity() >= 0)
        // .filter(g -> g.getListOfGoodsCar() != null)
        .isPresent();
  }
}
