package com.garage.acedetails.dto.customer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

  private Long id;

  private String firstName;

  private String lastName;

  private String address;

  private String phone;
}
