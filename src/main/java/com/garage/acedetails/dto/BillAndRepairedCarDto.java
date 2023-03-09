package com.garage.acedetails.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.garage.acedetails.dto.repairedcar.RepairedCarDto;
import lombok.Data;

@Data
public class BillAndRepairedCarDto extends BillDto {
  @JsonProperty("RepairedCar")
  private RepairedCarDto repairedCarDto;
}
