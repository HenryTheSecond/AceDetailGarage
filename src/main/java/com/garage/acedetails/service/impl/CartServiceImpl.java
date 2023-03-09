package com.garage.acedetails.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.garage.acedetails.entity.Cart;
import com.garage.acedetails.entity.CartDetails;
import com.garage.acedetails.mapper.CartMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.CartRepository;
import com.garage.acedetails.service.CartService;


@Service
public class CartServiceImpl implements CartService {
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartMapper cartMapper;

  @Override
  public DataResponse getCartByCustomerId(String strCustomerId) {
    Long customerId = Long.parseLong(strCustomerId);
    return new DataResponse(cartMapper.entityToCartDto(cartRepository.findByCustomerId(customerId)
        .orElseThrow(() -> new IllegalArgumentException("Cart does not exist"))));
  }

  @Override
  public Cart addNewCartDetails(CartDetails cartDetails) {
    Cart cart = this.getCartByCartId(cartDetails.getCartId());
    cart.setTotalCartDetails(cart.getTotalCartDetails() + 1);
    cart.setTotalPrice(cart.getTotalPrice() + cartDetails.getTotalPrice());
    cart.getSetOfCartDetails().add(cartDetails);
    return cartRepository.saveAndFlush(cart);
  }

  @Override
  public Cart deleteCartDetails(CartDetails cartDetails) {
    Cart cart = this.getCartByCartId(cartDetails.getCartId());
    cart.setTotalCartDetails(cart.getTotalCartDetails() - 1);
    cart.setTotalPrice(cart.getTotalPrice() - cartDetails.getTotalPrice());
    return cartRepository.saveAndFlush(cart);
  }

  @Override
  public Cart addOrSubtractQuantityOfCartDetails(CartDetails cartDetails, double oldTotalPrice) {
    Cart cart = this.getCartByCartId(cartDetails.getCartId());
    cart.setTotalCartDetails(cart.getTotalCartDetails());
    cart.setTotalPrice(cart.getTotalPrice() + cartDetails.getTotalPrice() - oldTotalPrice);
    // above == cart.getTotalPrice() - (oldTotalPrice - cartDetails.getTotalPrice()) when subtract
    return cartRepository.saveAndFlush(cart);
  }

  @Override
  public Cart createCart(Long customerId) {
    // Check cart exist
    Optional<Cart> cart = cartRepository.findByCustomerId(customerId);
    if (cart.isPresent() == true) {
      return cart.get();
    } else {
      // if cart does not exist create new one
      return cartRepository.save(new Cart(customerId));
    }
  }

  // check if cartId and customerId are in sync
  @Override
  public boolean check(Long cartId, Long customerId) {
    Cart cartFromCartId =
        cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Something wrong"));
    Cart cartFromCustomerId =
        cartRepository.findByCustomerId(customerId).orElseThrow(() -> new IllegalArgumentException("Something wrong"));
    if (cartFromCartId.getCustomerId() == customerId && cartFromCustomerId.getId() == cartId) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Cart getCartByCartId(Long cartId) {
    return cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart does not exist"));
  }
}
