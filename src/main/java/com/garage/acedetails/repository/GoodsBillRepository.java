package com.garage.acedetails.repository;

import com.garage.acedetails.entity.GoodsBill;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsBillRepository extends JpaRepository<GoodsBill, Long>, JpaSpecificationExecutor<GoodsBill> {
  @Query("SELECT goodsBill FROM GoodsBill goodsBill WHERE"
      + "(?1 IS NULL OR goodsBill.purchaseDate >= ?1) AND"
      + "(?2 IS NULL OR goodsBill.purchaseDate <= ?2)")
  public List<GoodsBill> findByPurchaseDate(LocalDate from, LocalDate to);
}
