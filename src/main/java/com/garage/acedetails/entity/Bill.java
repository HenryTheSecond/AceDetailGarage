package com.garage.acedetails.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bills")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Bill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "number", columnDefinition = "BIGINT(19)")
  private Long number;

  @Column(name = "payment_method", columnDefinition = "NVARCHAR(20)")
  @ExcelColumnIndex(index = 1)
  private String paymentMethod;

  @Column(name = "total_money", columnDefinition = "BIGINT(19)", nullable = false)
  @ExcelColumnIndex(index = 2)
  private Long totalMoney;

  @Column(name = "payment_date", columnDefinition = "DATE", nullable = false)
  @Temporal(TemporalType.DATE)
  @ExcelColumnIndex(index = 3)
  private Date paymentDate;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "repaired_car_id", nullable = false)
  @JsonIgnore
  private RepairedCar repairedCar;
}
