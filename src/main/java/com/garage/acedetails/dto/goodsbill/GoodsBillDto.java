package com.garage.acedetails.dto.goodsbill;

import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.util.PaymentMethod;
import com.garage.acedetails.util.annotation.ValueOfEnum;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBillDto {
  @NotBlank
  private String phone;
  @ValueOfEnum(enumClass = PaymentMethod.class, message = ApplicationConstants.PAYMENT_METHOD_INVALID)
  private String paymentMethod;
  @NotNull
  private LocalDate purchaseDate;
  private LocalDate paymentDate;
}
