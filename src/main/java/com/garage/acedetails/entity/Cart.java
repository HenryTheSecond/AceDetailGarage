package com.garage.acedetails.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "cart")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "customer_id", columnDefinition = "BIGINT(19)", unique = true, nullable = false)
  private Long customerId;

  @Column(nullable = false)
  @Min(0)
  private double totalPrice;

  @Column(name = "total_cart_details", nullable = false)
  @Min(0)
  private int totalCartDetails;

  @OneToMany(mappedBy = "cartId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<CartDetails> setOfCartDetails;


  public Cart(Long customerId) {
    this.totalPrice = 0;
    this.totalCartDetails = 0;
    this.customerId = customerId;
  }


}
