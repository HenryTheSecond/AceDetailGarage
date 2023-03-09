package com.garage.acedetails.entity;

import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "goods_Car")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoodsCar {
  @EmbeddedId
  private GoodsCarPK goodsCarPK;

  @Column(name = "quantity", nullable = false)
  @ExcelColumnIndex(index = 2)
  private int quantity;

  @Column(name = "price", nullable = false)
  @ExcelColumnIndex(index = 4)
  private double price;

  @Column(name = "total_price", nullable = false)
  @ExcelColumnIndex(index = 3)
  private double totalPrice;

  public GoodsCar(int quantity, double price, double totalPrice) {
    this.quantity = quantity;
    this.price = price;
    this.totalPrice = totalPrice;
  }
}
