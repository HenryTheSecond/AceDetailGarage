package com.garage.acedetails.dto.goodscar;

import com.garage.acedetails.entity.GoodsCarPK;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCarDTO {

	@NotNull
	private GoodsCarPK goodsCarPK;
	@DecimalMin(value = "0", inclusive = false)
	private int quantity;
	@DecimalMin(value = "0", inclusive = false)
	private double price;
	@DecimalMin(value = "0")
	private double totalPrice;

}
