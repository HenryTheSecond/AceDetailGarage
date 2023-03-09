package com.garage.acedetails.service;

import com.garage.acedetails.dto.customer.CustomerRequestDto;
import com.garage.acedetails.model.DataResponse;

public interface CustomerService {

  DataResponse insertCustomer(CustomerRequestDto customerRequestDto);

  DataResponse findAll(String strPageIndex);

  DataResponse getCustomer(String stringId);

  DataResponse getInformation(String stringId);

  DataResponse deleteCustomer(String stringId);

  DataResponse updateInformation(CustomerRequestDto customerRequestDto);
}
