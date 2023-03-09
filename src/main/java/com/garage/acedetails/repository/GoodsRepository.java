package com.garage.acedetails.repository;

import com.garage.acedetails.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
  @Query("SELECT goods FROM Goods goods WHERE"
      + "(?1 IS NULL OR goods.name LIKE CONCAT('%',?1,'%')) AND"
      + "(?2 IS NULL OR goods.price <= ?2) AND"
      + "(?3 IS NULL OR goods.price >= ?3) AND"
      + "(?4 IS NULL OR goods.category.number = ?4)")
  Page<Goods> searchAndFilterGoods(String keyword, Double maxPrice, Double minPrice, Long idCategory, Pageable page);
}
