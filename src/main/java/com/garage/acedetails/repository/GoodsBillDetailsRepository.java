package com.garage.acedetails.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.garage.acedetails.entity.GoodBillDetails;
import com.garage.acedetails.entity.GoodBillDetailsPK;

@Repository
public interface GoodsBillDetailsRepository extends JpaRepository<GoodBillDetails, GoodBillDetailsPK> {
  List<GoodBillDetails> findByGoodBillDetailsPK_Goodsbill(Long id, Pageable pageable);
}
