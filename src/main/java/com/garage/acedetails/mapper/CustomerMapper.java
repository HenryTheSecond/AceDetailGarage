package com.garage.acedetails.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.garage.acedetails.dto.customer.CustomerRequestDto;
import com.garage.acedetails.dto.customer.CustomerResponseDto;
import com.garage.acedetails.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerRequestDto entityToCustomerRequestDto(Customer customer);

  Customer customerRequestDtoToEntity(CustomerRequestDto customerRequestDto);

  CustomerResponseDto entityToCustomerResponseDto(Customer customer);

  List<CustomerResponseDto> listEntityToListCustomerResponseDtos(List<Customer> listOfCustomer);
}
