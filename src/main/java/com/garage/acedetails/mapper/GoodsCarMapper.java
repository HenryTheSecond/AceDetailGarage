package com.garage.acedetails.mapper;

import com.garage.acedetails.dto.goodscar.GoodsCarDTO;
import com.garage.acedetails.entity.GoodsCar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoodsCarMapper {

	GoodsCarDTO GoodsCarToGoodsCarDTO(GoodsCar goodsCar);

	GoodsCar GoodsCarDTOToGoodsCar(GoodsCarDTO goodsCarDTO);
}
