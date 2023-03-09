package com.garage.acedetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.dto.CartDetailsRequest;
import com.garage.acedetails.dto.CartDto;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.CartDetailsService;

@RestController
@RequestMapping("cart-details")
public class CartDetailsController {

  @Autowired
  private CartDetailsService cartDetailsService;

  @GetMapping("/get")
  @PreAuthorize("hasRole('ROLE_USER')")
  public DataResponse getCartDetail(@RequestBody CartDto cartDto) {
    return cartDetailsService.getAllCartDetails(cartDto);
  }

  @PostMapping("/add")
  @PreAuthorize("hasRole('ROLE_USER')")
  public DataResponse addCartDetails(@RequestBody CartDetailsRequest cartDetailsRequest) {
    return cartDetailsService.addCartDetails(cartDetailsRequest);
  }

  @PutMapping("/subtract")
  @PreAuthorize("hasRole('ROLE_USER')")
  public DataResponse subtractCartDetails(@RequestBody CartDetailsRequest cartDetailsRequest) {
    return cartDetailsService.subtractCartDetails(cartDetailsRequest);
  }

  @DeleteMapping("/delete")
  @PreAuthorize("hasRole('ROLE_USER')")
  public DataResponse deleteCartDetails(@RequestBody CartDetailsRequest cartDetailsRequest) {
    return cartDetailsService.deleteCartDetails(cartDetailsRequest);
  }
}
