package com.garage.acedetails.specification;

import com.garage.acedetails.entity.Employee;
import javax.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

	public static Specification<Employee> hasName(String name) {
		return ((root, query, criteriaBuilder) -> {
			Expression<String> fullName = criteriaBuilder.concat(root.get("firstName"), " ");
			fullName = criteriaBuilder.concat(fullName, root.get("lastName"));
			Expression<String> fullNameReverse = criteriaBuilder.concat(root.get("lastName"), " ");
			fullNameReverse = criteriaBuilder.concat(fullNameReverse, root.get("firstName"));
			return criteriaBuilder.or(
					criteriaBuilder.like(criteriaBuilder.lower(fullName), "%" + name.toLowerCase() + "%"),
					criteriaBuilder.like(criteriaBuilder.lower(fullNameReverse), "%" + name.toLowerCase() + "%"));
		});
	}

	public static Specification<Employee> hasId(Long id) {
		return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
	}
}
