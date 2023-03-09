package com.garage.acedetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
  @Autowired
  private CartService cartService;

  @GetMapping("/get/{id}")
  @PreAuthorize("hasRole('ROLE_USER')")
  public DataResponse getCart(@PathVariable(name = "id", required = true) String srtCustomerId) {
    return cartService.getCartByCustomerId(srtCustomerId);
  }
}
