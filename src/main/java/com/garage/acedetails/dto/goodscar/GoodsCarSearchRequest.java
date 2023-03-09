package com.garage.acedetails.dto.goodscar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCarSearchRequest {

	private Long goodsId;
	private Long carId;
}
