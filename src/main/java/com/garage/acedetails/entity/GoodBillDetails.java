package com.garage.acedetails.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goods_bill_details")
public class GoodBillDetails {
  @EmbeddedId
  private GoodBillDetailsPK goodBillDetailsPK;

  @Column(name = "quantity", nullable = false)
  private int quantity;

  @Column(name = "price", nullable = false)
  private double price;

  @Column(name = "total_price", nullable = false)
  private double totalPrice;

  public GoodBillDetails(double price, int quantity, double totalPrice) {
    this.price = price;
    this.quantity = quantity;
    this.totalPrice = totalPrice;
  }
}
