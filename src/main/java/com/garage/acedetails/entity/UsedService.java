package com.garage.acedetails.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UsedService {
  @EmbeddedId
  private UsedServicePK id = new UsedServicePK();

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("id")
  @JoinColumn(name = "repaired_car_id")
  @JsonIgnore
  @EqualsAndHashCode.Exclude
  private RepairedCar repairedCar;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("car_care_number")
  @JoinColumn(name = "car_care_number")
  @JsonIgnore
  @EqualsAndHashCode.Exclude
  private CarCare carCare;

  @ExcelColumnIndex(index = 2)
  private double carCarePrice;

  public UsedService(UsedServicePK id, double serPrice) {
    super();
    this.id = id;
    this.carCarePrice = serPrice;
  }
}
