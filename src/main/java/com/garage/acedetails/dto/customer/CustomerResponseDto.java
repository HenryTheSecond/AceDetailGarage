package com.garage.acedetails.dto.customer;

import java.util.List;
import com.garage.acedetails.entity.RepairedCar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {
  private Long id;
  private String firstName;
  private String lastName;
  private String address;
  private String phone;
  private List<RepairedCar> listRepairedCars;
}
