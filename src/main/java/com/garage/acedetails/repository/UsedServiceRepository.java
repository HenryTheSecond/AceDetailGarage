package com.garage.acedetails.repository;

import com.garage.acedetails.entity.UsedService;
import com.garage.acedetails.entity.UsedServicePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedServiceRepository extends JpaRepository<UsedService, UsedServicePK> {

}
