package com.garage.acedetails.entity;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Goods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Goods {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "number")
  private Long number;

  @NotNull(message = "Goods name mustn't be null")
  @NotBlank(message = "Goods name mustn't be blank")
  @Column(name = "name", columnDefinition = "NVARCHAR(80)", nullable = false)
  @ExcelColumnIndex(index = 1)
  private String name;


  @Column(name = "price", nullable = false)
  @ExcelColumnIndex(index = 2)
  private double price;

  @Column(name = "total_quantity", nullable = false)
  @ExcelColumnIndex(index = 3)
  private int totalQuantity;


  @Column(name = "description", columnDefinition = "TEXT", nullable = false)
  @ExcelColumnIndex(index = 4)
  private String description;

  @ManyToOne
  @JoinColumn(name = "category_number")
  private Category category;

  @OneToMany(mappedBy = "goodsCarPK.goods", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<GoodsCar> setOfGoodsCar;

  @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Image> listOfImage;
  
  @OneToMany(mappedBy = "goodBillDetailsPK.goods", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<GoodBillDetails> setOfGoodsBillDetails;
}
