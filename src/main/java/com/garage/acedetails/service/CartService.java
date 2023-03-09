package com.garage.acedetails.service;

import com.garage.acedetails.entity.Cart;
import com.garage.acedetails.entity.CartDetails;
import com.garage.acedetails.model.DataResponse;

public interface CartService {

  public DataResponse getCartByCustomerId(String strCustomerId);

  public Cart addNewCartDetails(CartDetails cartDetails);

  public Cart deleteCartDetails(CartDetails cartDetails);

  public Cart addOrSubtractQuantityOfCartDetails(CartDetails cartDetails, double oldTotalPrice);

  public Cart createCart(Long customerId);

  public boolean check(Long cartId, Long customerId);

  public Cart getCartByCartId(Long cartId);
}
