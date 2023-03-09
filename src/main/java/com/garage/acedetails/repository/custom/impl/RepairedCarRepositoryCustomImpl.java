package com.garage.acedetails.repository.custom.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.entity.RepairedCar;
import com.garage.acedetails.repository.custom.RepairedCarRepositoryCustom;

public class RepairedCarRepositoryCustomImpl implements RepairedCarRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Map<String, Object> searchRepairedCar(String keyword, String keywordType, List<Long> listIdCarCares,
      String dateType, LocalDateTime from, LocalDateTime to, int page, String orderBy, String order) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    
    //Create filter query and count query
    CriteriaQuery<RepairedCar> query = cb.createQuery(RepairedCar.class);
    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);

    Root<RepairedCar> root = query.from(RepairedCar.class);
    Root<RepairedCar> countRoot = countQuery.from(RepairedCar.class);

    //Although filter query and count query have the same where clause, but we have to create 2 list predicate separately
    List<Predicate> predicates = new ArrayList<>();
    List<Predicate> countPredicates = new ArrayList<>();

    //Filter by keyword, we have 3 options: filter by carType field, carNumber field or customer name
    if (keywordType != null) {
      if (keywordType.equals("carType")) { //Filter by carType
        predicates.add(cb.like(root.get("carType"), "%" + keyword + "%"));
        countPredicates.add(cb.like(countRoot.get("carType"), "%" + keyword + "%"));
      } else if (keywordType.equals("carNumber")) { //Filter by carNumber
        predicates.add(cb.like(root.get("carNumber"), "%" + keyword + "%"));
        countPredicates.add(cb.like(countRoot.get("carNumber"), "%" + keyword + "%"));
      } else if (keywordType.equals("customerName")) { //Filter by customer name by concat customer's first name and last name
        predicates.add(cb.like(
            cb.concat(cb.concat(root.get("customer").get("firstName"), " "), root.get("customer").get("lastName")),
            "%" + keyword + "%"));
        countPredicates.add(cb.like(cb.concat(cb.concat(countRoot.get("customer").get("firstName"), " "),
            countRoot.get("customer").get("lastName")), "%" + keyword + "%"));
      }
    }

    /*Filter by listIdCarCares which RepairedCar has used.
     *First select RepairedCar which used any car care in the list
     *then count group by id then compare to the size of listIdCarCares, if equal that means the RepairedCar used all the car care in the listIdCarCares*/
    if (listIdCarCares != null) {
      //Join repaired_car table and used_service table
      Path<Long> carCareNumberPath = root.join("setOfUsedService").get("id").get("carCareNumber");
      //select RepairedCar which used any car care in the list
      predicates.add(carCareNumberPath.in(listIdCarCares));
      //count group by id and compare to list size, select if the count and list size are equal
      query.groupBy(root.get("id")).having(cb.equal(cb.countDistinct(carCareNumberPath), listIdCarCares.size()));

      //Same as above but for countQuery
      Path<Long> countCarCareNumberPath = countRoot.join("setOfUsedService").get("id").get("carCareNumber");
      countPredicates.add(countCarCareNumberPath.in(listIdCarCares));
      countQuery.having(cb.equal(cb.countDistinct(countCarCareNumberPath), listIdCarCares.size()));
    }

    //Filter by date, we have 2 options: filter by startDate or endDate
    if (dateType != null && (dateType.equals("startDate") || dateType.equals("endDate"))) {
      if (from != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get(dateType), from));
        countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get(dateType), from));
      }
      if (to != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get(dateType), to));
        countPredicates.add(cb.lessThanOrEqualTo(countRoot.get(dateType), to));
      }
    }

    if (order.equals("asc")) {
      query.orderBy(cb.asc(root.get(orderBy)));
    } else {
      query.orderBy(cb.desc(root.get(orderBy)));
    }

    query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
    countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[predicates.size()]));

    Long count = entityManager.createQuery(countQuery).getSingleResult();
    System.out.println(count);
    int pageSize = ApplicationConstants.REPAIRED_CAR_PAGE_SIZE;
    Long maxPage = count % pageSize > 0 ? count / pageSize + 1 : count / pageSize;
    List<RepairedCar> listRepairedCar =
        entityManager.createQuery(query).setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList();

    Map<String, Object> result = new HashMap<>();
    result.put("maxPage", maxPage);
    result.put("repairedCars", listRepairedCar);
    return result;
  }
}
