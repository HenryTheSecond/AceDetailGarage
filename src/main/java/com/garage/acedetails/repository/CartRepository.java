package com.garage.acedetails.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.garage.acedetails.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  public Optional<Cart> findByCustomerId(Long customerId);
}
