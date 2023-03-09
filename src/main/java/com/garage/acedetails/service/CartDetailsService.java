package com.garage.acedetails.service;

import com.garage.acedetails.dto.CartDetailsRequest;
import com.garage.acedetails.dto.CartDto;
import com.garage.acedetails.model.DataResponse;



public interface CartDetailsService {

  public DataResponse getAllCartDetails(CartDto cartDto);

  public DataResponse addCartDetails(CartDetailsRequest cartDetailsRequest);

  public DataResponse subtractCartDetails(CartDetailsRequest cartDetailsRequest);

  public DataResponse deleteCartDetails(CartDetailsRequest cartDetailsRequest);
}
