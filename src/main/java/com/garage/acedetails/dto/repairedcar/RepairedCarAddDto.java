package com.garage.acedetails.dto.repairedcar;

import java.util.List;
import com.garage.acedetails.dto.customer.CustomerRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairedCarAddDto extends RepairedCarDto {
  
  private CustomerRequestDto customer;
  private List<Long> listIdCarCare;
}
