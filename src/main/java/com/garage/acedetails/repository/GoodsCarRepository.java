package com.garage.acedetails.repository;

import com.garage.acedetails.entity.GoodsCar;
import com.garage.acedetails.entity.GoodsCarPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsCarRepository extends JpaRepository<GoodsCar, GoodsCarPK>, JpaSpecificationExecutor<GoodsCar> {

}
