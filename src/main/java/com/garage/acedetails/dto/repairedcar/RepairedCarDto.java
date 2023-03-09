package com.garage.acedetails.dto.repairedcar;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairedCarDto {
  private Long id;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String description;
  
  @NotBlank(message = "Car type must not blank")
  private String carType;
  private String carNumber;
  private String carStatus;
}
