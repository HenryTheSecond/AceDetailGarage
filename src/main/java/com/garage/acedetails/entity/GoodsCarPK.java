package com.garage.acedetails.entity;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCarPK implements Serializable {
  @ManyToOne
  @JoinColumn(name = "goods_number")
  private Goods goods;

  @ManyToOne
  @JoinColumn(name = "repaired_car_id")
  private RepairedCar repairedCar;

  @Override
  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = result * prime + Long.hashCode(goods.getNumber());
    result = result * prime + Long.hashCode(repairedCar.getId());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    GoodsCarPK goodsCarPK = (GoodsCarPK) obj;
    if (this.goods.getNumber() != goodsCarPK.getGoods().getNumber()) {
      return false;
    }
    return Objects.equals(this.repairedCar.getId(), goodsCarPK.getRepairedCar().getId());
  }
}
