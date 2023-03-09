package com.garage.acedetails.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.dto.customer.CustomerRequestDto;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.CustomerService;

@RequestMapping("/customer")
@RestController
public class CustomerController {
  @Autowired
  private CustomerService customerService;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/add")
  public DataResponse addCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
    return customerService.insertCustomer(customerRequestDto);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_EMPLOYEE')")
  @GetMapping("/get/{id}")
  public DataResponse getCustomer(@PathVariable(name = "id", required = true) String stringId) {
    return customerService.getCustomer(stringId);
  }

  @PreAuthorize("hasRole('ROLE_USER')")
  @GetMapping("/get-information/{id}")
  public DataResponse getInformation(@PathVariable(name = "id", required = true) String stringId) {
    return customerService.getInformation(stringId);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
  @GetMapping("/list/{pageIndex}")
  public DataResponse getAllCustomer(@PathVariable(name = "pageIndex", required = true) String strPageIndex) {
    return customerService.findAll(strPageIndex);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/delete/{id}")
  public DataResponse deleteCustomer(@PathVariable(name = "id", required = true) String stringId) {
    return customerService.deleteCustomer(stringId);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_EMPLOYEE')")
  @PutMapping("/update")
  public DataResponse updateCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
    return customerService.updateInformation(customerRequestDto);
  }
}
