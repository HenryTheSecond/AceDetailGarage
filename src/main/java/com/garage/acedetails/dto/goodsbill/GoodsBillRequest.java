package com.garage.acedetails.dto.goodsbill;

import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.entity.Customer;
import com.garage.acedetails.util.PaymentMethod;
import com.garage.acedetails.util.annotation.ValueOfEnum;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBillRequest {

	@ValueOfEnum(enumClass = PaymentMethod.class, message = ApplicationConstants.PAYMENT_METHOD_INVALID)
	private String paymentMethod;

	private Double total;

	@NotNull
	private LocalDate purchaseDate;

	private LocalDate paymentDate;

	@NotNull
	private Customer customer;
}
