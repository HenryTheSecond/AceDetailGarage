package com.garage.acedetails.controller;

import com.garage.acedetails.dto.goodscar.GoodsCarDTO;
import com.garage.acedetails.dto.goodscar.GoodsCarSearchRequest;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.GoodsCarService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods-car")
public class GoodsCarController {

	@Autowired
	private GoodsCarService goodsCarService;

	@GetMapping("/find")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_EMPLOYEE', 'ROLE_ADMIN')")
	public DataResponse findGoodsCar(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestBody GoodsCarSearchRequest goodsCarSearchRequest) {
		return goodsCarService.findGoodsCar(page, goodsCarSearchRequest);
	}

	@PostMapping("/add")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	public DataResponse insertGoodsCar(@Valid @RequestBody GoodsCarDTO goodsCarDTO) {
		return goodsCarService.insertGoodsCar(goodsCarDTO);
	}

	@PutMapping("/update")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	public DataResponse updateGoodsCar(
			@Valid @RequestBody GoodsCarDTO goodsCarDTO,
			@RequestParam(name = "goods-id", defaultValue = "0") long goodsId,
			@RequestParam(name = "car-id", defaultValue = "0") long carId) {
		return goodsCarService.updateGoodsCar(goodsId, carId, goodsCarDTO);
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	public DataResponse deleteGoodsCar(
			@RequestParam(name = "goods-id", defaultValue = "0") long goodsId,
			@RequestParam(name = "car-id", defaultValue = "0") long carId
	) {
		return goodsCarService.deleteGoodsCar(goodsId, carId);
	}
}
