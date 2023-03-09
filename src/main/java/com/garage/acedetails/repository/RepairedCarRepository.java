package com.garage.acedetails.repository;

import com.garage.acedetails.entity.RepairedCar;
import com.garage.acedetails.repository.custom.RepairedCarRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairedCarRepository extends JpaRepository<RepairedCar, Long>, RepairedCarRepositoryCustom {

}
