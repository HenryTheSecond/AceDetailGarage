package com.garage.acedetails.specification;

import com.garage.acedetails.entity.Customer;
import com.garage.acedetails.entity.GoodsBill;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public class GoodsBillSpecification {

  // Filter by name
  public static Specification<GoodsBill> hasCustomer(Customer customer) {
    return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customer"), customer));
  }

  // Filter by id
  public static Specification<GoodsBill> hasId(Long id) {
    return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
  }

  public static Specification<GoodsBill> fromPurchaseDate(LocalDate date) {
    return (((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("purchaseDate"), date)));
  }

  public static Specification<GoodsBill> toPurchaseDate(LocalDate date) {
    return (((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("purchaseDate"), date)));
  }
}
