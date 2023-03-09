package com.garage.acedetails.entity;

import java.io.Serializable;
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
public class GoodBillDetailsPK implements Serializable{
  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "goods_number")
  private Goods goods;

  @ManyToOne
  @JoinColumn(name = "goods_bill_number")
  private GoodsBill goodsbill;
}
