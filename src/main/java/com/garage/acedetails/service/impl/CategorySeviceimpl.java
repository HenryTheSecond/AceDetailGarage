package com.garage.acedetails.service.impl;

import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.category.CategoryDto;
import com.garage.acedetails.entity.Category;
import com.garage.acedetails.mapper.CategoryMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.CategoryRepository;
import com.garage.acedetails.service.CategorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CategorySeviceimpl implements CategorySevice {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  public DataResponse findAll() {
    return new DataResponse(categoryRepository.findAll());
  }

  @Override
  public DataResponse findByID(String StId) {
    Long id = Long.parseLong(StId);
    Category categoryService =
        categoryRepository.findById(id).orElseThrow(() -> new RuntimeException(ApplicationConstants.NOT_FOUND));
    return new DataResponse(categoryService);
  }

  @Override
  public DataResponse insertCategory(CategoryDto categoryDto) {
    String categoryName = categoryDto.getName();
    if (categoryRepository.findByName(categoryName) != null) {
      throw new RuntimeException(ApplicationConstants.IS_DUPLICATED);
    }
    return new DataResponse(categoryRepository.save(categoryMapper.categoryDtoToEntity(categoryDto)));
  }

  @Override
  public DataResponse deleteCategory(String StId) {
    try {
      Long id = Long.parseLong(StId);
      categoryRepository.deleteById(id);
      return DataResponse.SUCCESSFUL;
    } catch (EmptyResultDataAccessException e) {
      return DataResponse.BAD_REQUEST;
    }
  }

  @Override
  public DataResponse updateCategory(CategoryDto categoryDto, String StId) {
    // Check if exist category in Database
    Long id = Long.parseLong(StId);
    Category currentCategory =
        categoryRepository.findById(id).orElseThrow(() -> new RuntimeException(ApplicationConstants.NOT_FOUND));
    // Update new value
    currentCategory.setNumber(categoryDto.getNumber() == null ? currentCategory.getNumber() : categoryDto.getNumber());
    currentCategory.setName(categoryDto.getName() == null ? currentCategory.getName() : categoryDto.getName());
    return new DataResponse(categoryRepository.saveAndFlush(currentCategory));
  }

}
