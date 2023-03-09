package com.garage.acedetails.service;

import com.garage.acedetails.dto.goodscar.GoodsCarDTO;
import com.garage.acedetails.dto.goodscar.GoodsCarSearchRequest;
import com.garage.acedetails.model.DataResponse;
import org.springframework.stereotype.Service;

@Service
public interface GoodsCarService {

	DataResponse findGoodsCar(int page, GoodsCarSearchRequest goodsCarSearchRequest);

	DataResponse insertGoodsCar(GoodsCarDTO goodsCarDTO);

	DataResponse updateGoodsCar(long goodsId, long carId, GoodsCarDTO goodsCarDTO);

	DataResponse deleteGoodsCar(long goodsId, long carId);
}
