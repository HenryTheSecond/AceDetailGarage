package com.garage.acedetails.mapper;

import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.goodsbill.GoodsBillDto;
import com.garage.acedetails.dto.goodsbill.GoodsBillRequest;
import com.garage.acedetails.dto.goodsbill.GoodsBillResponse;
import com.garage.acedetails.entity.Customer;
import com.garage.acedetails.entity.GoodsBill;
import com.garage.acedetails.repository.CustomerRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

@Mapper(componentModel = "spring")
public abstract class GoodsBillMapper {

	@Autowired
	protected CustomerRepository customerRepository;

	public abstract GoodsBill GoodsBillRequestToGoodsBill(GoodsBillRequest goodsBillRequest);

	public abstract GoodsBillResponse GoodsBillToGoodsBillResponse(GoodsBill goodsBill);

	public abstract GoodsBill GoodsBillDtoToGoodsBill(GoodsBillDto goodsBillDto);

	@BeforeMapping
	protected void enrichGoodsBillRequestWithCustomer(
			GoodsBillRequest goodsBillRequest,
			@MappingTarget GoodsBill goodsBill) {
		Long id = goodsBillRequest.getCustomer().getId();
		Customer customer = customerRepository
				.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(ApplicationConstants.CUSTOMER_NOT_FOUND, 1));
		goodsBillRequest.setCustomer(customer);
	}

	@BeforeMapping
	protected void  enrichGoodsBillDTOWithCustomer(GoodsBillDto goodsBillDto, @MappingTarget GoodsBill goodsBill) {
		String phone = goodsBillDto.getPhone();
		Customer customer = customerRepository
				.findByPhone(phone)
				.orElseThrow(() -> new EmptyResultDataAccessException(ApplicationConstants.CUSTOMER_NOT_FOUND, 1));
		goodsBill.setCustomer(customer);
	}
}
