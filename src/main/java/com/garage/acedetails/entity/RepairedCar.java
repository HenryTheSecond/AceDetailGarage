package com.garage.acedetails.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
// @JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class RepairedCar {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "start_date")
  @ExcelColumnIndex(index = 1)
  private LocalDateTime startDate;

  @Column(name = "end_date")
  @ExcelColumnIndex(index = 2)
  private LocalDateTime endDate;

  @Column(name = "description")
  @ExcelColumnIndex(index = 3)
  private String description;

  @Column(name = "car_type")
  @NotBlank(message = "Car type must not blank")
  @ExcelColumnIndex(index = 4)
  private String carType;

  @Column(name = "car_number")
  @ExcelColumnIndex(index = 5)
  private String carNumber;

  @Column(name = "car_status")
  @ExcelColumnIndex(index = 6)
  private String carStatus;

  @OneToMany(mappedBy = "repairedCar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<UsedService> setOfUsedService;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  @JsonIgnore
  private Customer customer;

  @OneToOne(mappedBy = "repairedCar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private Bill bill;

  @OneToMany(mappedBy = "goodsCarPK.repairedCar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<GoodsCar> setOfGoodsCars;

  public RepairedCar(LocalDateTime startDate, String description, String carType, String carNumber, String carStatus) {
    super();
    this.startDate = startDate;
    this.description = description;
    this.carType = carType;
    this.carNumber = carNumber;
    this.carStatus = carStatus;
  }

  @Override
  public String toString() {
    return "RepairedCar [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", description="
        + description + ", type=" + carType + ", carNumber=" + carNumber + ", carStatus=" + carStatus + "]";
  }

  // public RepairedCar(LocalDateTime startDate, String description, String carType, String carNumber,
  // String carStatus) {
  // super();
  // this.startDate = startDate;
  // this.description = description;
  // this.carType = carType;
  // this.carNumber = carNumber;
  // this.carStatus = carStatus;
  // }
  //
  //
  // @Override
  // public String toString() {
  // return "RepairedCar [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ",
  // description="
  // + description + ", type=" + carType + ", carNumber=" + carNumber + ", carStatus=" + carStatus +
  // "]";
  // }

}
