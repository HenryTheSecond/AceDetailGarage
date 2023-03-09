package com.garage.acedetails.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.garage.acedetails.entity.CartDetails;
import com.garage.acedetails.entity.CartDetails.CartDetailsId;


@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, CartDetailsId> {
  public List<CartDetails> findByCartId(Long cartId);
}
