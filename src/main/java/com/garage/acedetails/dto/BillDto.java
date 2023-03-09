package com.garage.acedetails.dto;

import java.util.Date;
import com.garage.acedetails.dto.repairedcar.RepairedCarDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {

  private Long number;
  private String paymentMethod;
  private Long totalMoney;
  private Date paymentDate;
  private Long repairedCarId;

}
