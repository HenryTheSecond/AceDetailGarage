package com.garage.acedetails.service.impl;

import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.goodscar.GoodsCarDTO;
import com.garage.acedetails.dto.goodscar.GoodsCarSearchRequest;
import com.garage.acedetails.entity.Goods;
import com.garage.acedetails.entity.GoodsCar;
import com.garage.acedetails.entity.GoodsCarPK;
import com.garage.acedetails.entity.RepairedCar;
import com.garage.acedetails.mapper.GoodsCarMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.GoodsCarRepository;
import com.garage.acedetails.repository.GoodsRepository;
import com.garage.acedetails.repository.RepairedCarRepository;
import com.garage.acedetails.service.GoodsCarService;
import com.garage.acedetails.specification.GoodsCarSpecification;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class GoodsCarServiceImpl implements GoodsCarService {

	@Autowired
	private GoodsCarRepository goodsCarRepository;

	@Autowired
	private GoodsRepository goodsRepository;

	@Autowired
	private RepairedCarRepository repairedCarRepository;

	@Autowired
	private GoodsCarMapper goodsCarMapper;

	@Override
	public DataResponse findGoodsCar(int page, GoodsCarSearchRequest goodsCarSearchRequest) {
		page = page > 0 ? (page - 1) : 0;
		Long goodsId = goodsCarSearchRequest.getGoodsId();
		Long carId = goodsCarSearchRequest.getCarId();
		Specification<GoodsCar> conditions = null;
		if (goodsId != null) {
			Goods goods = getGoodsFromDB(goodsId);
			conditions = Specification.where(GoodsCarSpecification.hasGoods(goods));
		}
		if (carId != null) {
			RepairedCar repairedCar = getRepairedCarFromDB(carId);
			conditions = conditions == null ?
					Specification.where(GoodsCarSpecification.hasCar(repairedCar)) :
					conditions.and(GoodsCarSpecification.hasCar(repairedCar));
		}
		List<GoodsCarDTO> listOfGoodsCarDTO =
				goodsCarRepository
						.findAll(conditions, PageRequest.of(page, ApplicationConstants.GOODS_CAR_PAGE_SIZE))
						.getContent()
						.stream()
						.map(goodsCarMapper::GoodsCarToGoodsCarDTO)
						.collect(Collectors.toList());
		return listOfGoodsCarDTO.size() > 0 ? new DataResponse(listOfGoodsCarDTO) : DataResponse.NOT_FOUND;
	}

	@Override
	public DataResponse insertGoodsCar(GoodsCarDTO goodsCarDTO) {
		GoodsCar goodsCarToInsert = goodsCarMapper.GoodsCarDTOToGoodsCar(goodsCarDTO);
		if (goodsCarToInsert.getGoodsCarPK().getGoods() == null) {
			throw new RuntimeException(ApplicationConstants.GOODS_NOT_FOUND);
		}
		if (goodsCarToInsert.getGoodsCarPK().getRepairedCar() == null) {
			throw new RuntimeException(ApplicationConstants.CAR_NOT_FOUND);
		}
		enrichGoodsCarPK(goodsCarToInsert);
		if (checkIfGoodsCarExisted(goodsCarToInsert.getGoodsCarPK())) {
			return new DataResponse(
					ApplicationConstants.BAD_REQUEST,
					ApplicationConstants.GOODS_CAR_EXISTED,
					ApplicationConstants.BAD_REQUEST_CODE
			);
		}
		goodsCarToInsert.setTotalPrice(goodsCarToInsert.getPrice() * goodsCarToInsert.getQuantity());
		return Optional.ofNullable(goodsCarRepository.save(goodsCarToInsert))
				.map(DataResponse::new)
				.orElseGet(() -> DataResponse.FAILED);
	}

	@Override
	public DataResponse updateGoodsCar(long goodsId, long carId, GoodsCarDTO goodsCarDTO) {
		GoodsCar goodsCarToUpdate = goodsCarMapper.GoodsCarDTOToGoodsCar(goodsCarDTO);
		Goods goods = getGoodsFromDB(goodsId);
		RepairedCar car = getRepairedCarFromDB(carId);
		GoodsCar currentGoodsCar = goodsCarRepository.findById(new GoodsCarPK(goods, car))
				.orElseThrow(() -> new RuntimeException(ApplicationConstants.GOODS_CAR_NOT_FOUND));
		currentGoodsCar.setPrice(goodsCarToUpdate.getPrice());
		currentGoodsCar.setQuantity(goodsCarToUpdate.getQuantity());
		currentGoodsCar.setTotalPrice(goodsCarToUpdate.getPrice() * goodsCarToUpdate.getQuantity());
		return Optional.ofNullable(goodsCarRepository.saveAndFlush(currentGoodsCar))
				.map(DataResponse::new)
				.orElseGet(() -> DataResponse.FAILED);
	}

	@Override
	public DataResponse deleteGoodsCar(long goodsId, long carId) {
		Goods goods = getGoodsFromDB(goodsId);
		RepairedCar car = getRepairedCarFromDB(carId);
		if (!checkIfGoodsCarExisted(new GoodsCarPK(goods, car))) {
			return DataResponse.NOT_FOUND;
		}
		goodsCarRepository.deleteById(new GoodsCarPK(goods, car));
		return DataResponse.SUCCESSFUL;
	}

	private void enrichGoodsCarPK(GoodsCar goodsCar) {
		Goods goods = getGoodsFromDB(goodsCar.getGoodsCarPK().getGoods().getNumber());
		RepairedCar car = getRepairedCarFromDB(goodsCar.getGoodsCarPK().getRepairedCar().getId());
		goodsCar.getGoodsCarPK().setGoods(goods);
		goodsCar.getGoodsCarPK().setRepairedCar(car);
	}

	private boolean checkIfGoodsCarExisted(GoodsCarPK goodsCarPK) {
		return goodsCarRepository.findById(goodsCarPK).isPresent();
	}

	private Goods getGoodsFromDB(long goodsId) {
		return goodsRepository
				.findById(goodsId)
				.orElseThrow(() -> new RuntimeException(ApplicationConstants.GOODS_NOT_FOUND));
	}

	private RepairedCar getRepairedCarFromDB(long carId) {
		return repairedCarRepository
				.findById(carId)
				.orElseThrow(() -> new RuntimeException(ApplicationConstants.CAR_NOT_FOUND));
	}
}
