package com.garage.acedetails.dto.repairedcar;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.garage.acedetails.dto.customer.CustomerRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairedCarAndCustomerDto extends RepairedCarDto{
  
  @JsonProperty("customer")
  private CustomerRequestDto customer;

}
