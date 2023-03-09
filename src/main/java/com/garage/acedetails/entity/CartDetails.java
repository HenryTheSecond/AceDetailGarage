package com.garage.acedetails.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.garage.acedetails.entity.CartDetails.CartDetailsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "cart_details")
@IdClass(CartDetailsId.class)
public class CartDetails {

  @Id
  private Long cartId;

  @Id
  @ManyToOne()
  @JoinColumn(name = "goods_id", columnDefinition = "BIGINT(19)")
  private Goods goods;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private double price;

  @Column(nullable = false)
  private double totalPrice;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CartDetailsId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long cartId;
    private Long goods;
  }
}
