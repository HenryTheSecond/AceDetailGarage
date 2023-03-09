package com.garage.acedetails.mapper;

import com.garage.acedetails.dto.goods.GoodsAddDto;
import com.garage.acedetails.dto.goods.GoodsSimpleInfoDto;
import com.garage.acedetails.entity.Goods;

public interface GoodsMapper {
  Goods goodsAddDtoToEntity(GoodsAddDto goodsAddDto);
  GoodsSimpleInfoDto entityToGoodsSimpleInfo(Goods goods);
}
