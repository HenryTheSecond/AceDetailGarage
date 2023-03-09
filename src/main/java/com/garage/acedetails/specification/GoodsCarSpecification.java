package com.garage.acedetails.specification;

import com.garage.acedetails.entity.Goods;
import com.garage.acedetails.entity.GoodsCar;
import com.garage.acedetails.entity.RepairedCar;
import org.springframework.data.jpa.domain.Specification;

public class GoodsCarSpecification {

	// Filter by goods
	public static Specification<GoodsCar> hasGoods(Goods goods) {
		return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("goodsCarPK").get("goods"), goods));
	}

	// Filter by car
	public static Specification<GoodsCar> hasCar(RepairedCar car) {
		return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("goodsCarPK").get("repairedCar"), car));
	}
}
