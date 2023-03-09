package com.garage.acedetails.dto.goodsbill;

import com.garage.acedetails.entity.Customer;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBillResponse {

	private Long id;
	private String paymentMethod;
	private Double total;
	private LocalDate purchaseDate;
	private LocalDate paymentDate;
	private Customer customer;
}
