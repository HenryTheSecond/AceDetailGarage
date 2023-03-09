package com.garage.acedetails.repository;

import com.garage.acedetails.entity.CarCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.garage.acedetails.entity.CarCare;

@Repository
public interface CarCareRepository extends JpaRepository<CarCare, Long> {
  public CarCare findByServiceName(String serviceName);

}
