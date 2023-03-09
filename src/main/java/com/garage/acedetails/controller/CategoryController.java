package com.garage.acedetails.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.dto.category.CategoryDto;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.CategorySevice;
import com.garage.acedetails.util.ValidId;
import lombok.RequiredArgsConstructor;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
@Validated
public class CategoryController {
  @Autowired
  private CategorySevice categorySevice;

  @GetMapping("/categogyList")
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse findAll() {
    return categorySevice.findAll();
  }

  @GetMapping("/find-byid/{id}")
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse findById(@PathVariable("id") @ValidId String strId) {
    return categorySevice.findByID(strId);
  }

  @PostMapping("/add")
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse insertCategory(@Validated @RequestBody CategoryDto categoryDto) {
    return categorySevice.insertCategory(categoryDto);
  }

  @DeleteMapping("/delete")
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse deleteCategory(@PathVariable("id") @ValidId String strId) {
    return categorySevice.deleteCategory(strId);
  }

  @PutMapping("/update/{id}")
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse updateCategory(@PathVariable("id") @ValidId String strId,
      @RequestBody @Valid CategoryDto categoryDto) {
    return categorySevice.updateCategory(categoryDto, strId);
  }
}
