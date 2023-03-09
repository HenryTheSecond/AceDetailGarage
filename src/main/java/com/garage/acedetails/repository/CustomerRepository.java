package com.garage.acedetails.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.garage.acedetails.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByPhone(String phone);
}
