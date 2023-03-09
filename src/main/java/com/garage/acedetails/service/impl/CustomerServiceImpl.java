package com.garage.acedetails.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.customer.CustomerRequestDto;
import com.garage.acedetails.dto.customer.CustomerResponseDto;
import com.garage.acedetails.entity.Customer;
import com.garage.acedetails.mapper.CustomerMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.CustomerRepository;
import com.garage.acedetails.service.CustomerService;


@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private CustomerMapper customerMapper;

  @Transactional
  public DataResponse insertCustomer(CustomerRequestDto customerRequestDto) {
    Customer customer = customerMapper.customerRequestDtoToEntity(customerRequestDto);
    return new DataResponse(customerRepository.save(customer));
  }

  @Transactional
  public DataResponse findAll(String strPageIndex) {
    int pageIndex = Integer.parseInt(strPageIndex);
    Pageable pageable = PageRequest.of(pageIndex, ApplicationConstants.PAGE_SIZE_OF_GET_ALL_CUSTOMER);
    Page<Customer> pageCustomer = customerRepository.findAll(pageable);
    if ((pageIndex <= (pageCustomer.getTotalPages() - 1) && pageCustomer.hasContent())) {
      List<Customer> listCustomer = pageCustomer.getContent();
      List<CustomerResponseDto> listOfCustomerResponseDto =
          customerMapper.listEntityToListCustomerResponseDtos(listCustomer);
      return new DataResponse(listOfCustomerResponseDto);
    } else {
      return DataResponse.BAD_REQUEST;
    }
  }

  public DataResponse getCustomer(String stringId) {
    Long id = Long.parseLong(stringId);
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(String.format("Customer id %x does not exists", id)));
    CustomerResponseDto customerResponseDto = customerMapper.entityToCustomerResponseDto(customer);
    return new DataResponse(customerResponseDto);
  }

  public DataResponse getInformation(String stringId) {
    Long id = Long.parseLong(stringId);
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(String.format("Customer id %x does not exists", id)));
    return new DataResponse(customerMapper.entityToCustomerRequestDto(customer));
  }

  @Transactional
  public DataResponse deleteCustomer(String stringId) {
    Long id = Long.parseLong(stringId);
    customerRepository.deleteById(id);
    return new DataResponse(true);
  }

  public DataResponse updateInformation(CustomerRequestDto customerRequestDto) {
    Customer currentCustomer = customerRepository.findById(customerRequestDto.getId())
        .orElseThrow(() -> new IllegalArgumentException(String.format("Customer does not exists")));
    Customer customer = customerMapper.customerRequestDtoToEntity(customerRequestDto);
    currentCustomer
        .setFirstName(customer.getFirstName() == null ? currentCustomer.getFirstName() : customer.getFirstName());
    currentCustomer
        .setLastName(customer.getLastName() == null ? currentCustomer.getLastName() : customer.getLastName());
    currentCustomer.setAddress(customer.getAddress() == null ? currentCustomer.getAddress() : customer.getAddress());
    currentCustomer.setPhone(customer.getPhone() == null ? currentCustomer.getPhone() : customer.getPhone());
    return new DataResponse(customerRepository.saveAndFlush(currentCustomer));
  }
}
