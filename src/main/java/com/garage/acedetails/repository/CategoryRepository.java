package com.garage.acedetails.repository;

import com.garage.acedetails.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  public Category findByName(String name);
}
