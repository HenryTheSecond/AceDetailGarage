package com.garage.acedetails.dto.repairedcar;

import java.util.Set;
import com.garage.acedetails.dto.carcare.CarCareDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairedCarAndCarCareDto extends RepairedCarDto{
  private Set<CarCareDto> setOfCarCare;
}
