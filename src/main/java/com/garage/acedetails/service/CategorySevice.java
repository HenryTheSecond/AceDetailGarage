package com.garage.acedetails.service;


import com.garage.acedetails.dto.category.CategoryDto;
import com.garage.acedetails.model.DataResponse;
import org.springframework.stereotype.Service;

@Service
public interface CategorySevice {

  DataResponse findAll();

  DataResponse findByID(String StId);

  DataResponse insertCategory(CategoryDto categoryDto);

  DataResponse deleteCategory(String StId);

  DataResponse updateCategory(CategoryDto categoryDto, String StId);
}
