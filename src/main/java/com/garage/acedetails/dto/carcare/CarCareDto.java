package com.garage.acedetails.dto.carcare;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarCareDto {
  private Long number;

  @NotNull(message = "Service name must not be null")
  @NotBlank(message = "Service name must not be blank")
  private String serviceName;

  private double price;
  private String description;
}
