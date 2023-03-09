package com.garage.acedetails.mapper;

import org.mapstruct.Mapper;
import com.garage.acedetails.dto.category.CategoryDto;
import com.garage.acedetails.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  CategoryDto entityToCategoryDto(Category category);
  Category categoryDtoToEntity(CategoryDto categoryDto);
}
