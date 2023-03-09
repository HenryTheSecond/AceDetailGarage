package com.garage.acedetails.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedServicePK implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @Column(name = "repaired_car_id")
  private Long repairedCarId;
  @Column(name = "car_care_number")
  private Long carCareNumber;
}
