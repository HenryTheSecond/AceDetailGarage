package com.garage.acedetails.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.CartDetailsRequest;
import com.garage.acedetails.dto.CartDto;
import com.garage.acedetails.entity.Cart;
import com.garage.acedetails.entity.CartDetails;
import com.garage.acedetails.entity.CartDetails.CartDetailsId;
import com.garage.acedetails.entity.Goods;
import com.garage.acedetails.mapper.CartMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.CartDetailsRepository;
import com.garage.acedetails.repository.GoodsRepository;
import com.garage.acedetails.service.CartDetailsService;
import com.garage.acedetails.service.CartService;


@Service
public class CartDetailsServiceImpl implements CartDetailsService {
  @Autowired
  private CartDetailsRepository cartDetailsRepository;

  @Autowired
  private CartService cartService;

  @Autowired
  private CartMapper cartMapper;

  @Autowired
  private GoodsRepository goodsRepository;

  // get All CartDetails of Cart by cart number
  private Set<CartDetails> getAllByCartId(Long cartId) {
    return new HashSet<CartDetails>(cartDetailsRepository.findByCartId(cartId));
  }

  @Override
  public DataResponse getAllCartDetails(CartDto cartDto) {
    Cart cart = cartMapper.cartDtoToEntity(cartDto);
    Set<CartDetails> setOfCartDetails = this.getAllByCartId(cart.getId());
    // check cart number and customer id from cartDto
    if (cartService.check(cartDto.getId(), cartDto.getCustomerId()) == true) {
      cart = cartService.getCartByCartId(cartDto.getId());
      cart.setSetOfCartDetails(setOfCartDetails);
      return new DataResponse(cart);
    } else {
      return new DataResponse(ApplicationConstants.BAD_REQUEST, "Wrong cart id or customer id");
    }
  }

  @Override
  @Transactional
  public DataResponse addCartDetails(CartDetailsRequest cartDetailsRequest) {
    // get Optional to check existence or not
    Optional<CartDetails> optionalCartDetails = cartDetailsRepository
        .findById(new CartDetailsId(cartDetailsRequest.getCartId(), cartDetailsRequest.getGoodsId()));
    Goods goods = goodsRepository.findById(cartDetailsRequest.getGoodsId()).get();
    // Check the quantity added is NOT >= totalQuantity of goods
    if (goods.getTotalQuantity() >= cartDetailsRequest.getQuantity()) {
      // Check isPresent() to decide whether to add new or add
      if (optionalCartDetails.isPresent() == true) {
        CartDetails cartDetails = optionalCartDetails.get();
        // save the old price to subtract when update Cart
        double oldTotalPrice = cartDetails.getTotalPrice();
        cartDetails.setTotalPrice(goods.getPrice() * (cartDetailsRequest.getQuantity() + cartDetails.getQuantity()));
        cartDetails.setQuantity(cartDetailsRequest.getQuantity() + cartDetails.getQuantity());
        cartDetails.setPrice(goods.getPrice());
        cartDetailsRepository.saveAndFlush(cartDetails);
        return new DataResponse(cartService.addOrSubtractQuantityOfCartDetails(cartDetails, oldTotalPrice));
      } else {
        double totalPrice = (goods.getPrice() * cartDetailsRequest.getQuantity());
        return new DataResponse(
            cartService.addNewCartDetails(cartDetailsRepository.save(new CartDetails(cartDetailsRequest.getCartId(),
                goods, cartDetailsRequest.getQuantity(), goods.getPrice(), totalPrice))));
      }
    } else {
      return new DataResponse(optionalCartDetails.get());
    }
  }

  @Override
  public DataResponse subtractCartDetails(CartDetailsRequest cartDetailsRequest) {
    CartDetails cartDetails = cartDetailsRepository
        .findById(new CartDetailsId(cartDetailsRequest.getCartId(), cartDetailsRequest.getGoodsId()))
        .orElseThrow(() -> new IllegalArgumentException("Goods has not been added to the cart"));
    if (cartDetails.getQuantity() > cartDetailsRequest.getQuantity()) {
      double oldTotalPrice = cartDetails.getTotalPrice();
      cartDetails.setTotalPrice(
          cartDetails.getGoods().getPrice() * (cartDetails.getQuantity() - cartDetailsRequest.getQuantity()));
      cartDetails.setQuantity(cartDetails.getQuantity() - cartDetailsRequest.getQuantity());
      cartDetails.setPrice(cartDetails.getGoods().getPrice());
      cartDetailsRepository.saveAndFlush(cartDetails);
      return new DataResponse(cartService.addOrSubtractQuantityOfCartDetails(cartDetails, oldTotalPrice));
    } else {
      return new DataResponse(null);
    }
  }

  @Override
  public DataResponse deleteCartDetails(CartDetailsRequest cartDetailsRequest) {
    CartDetails cartDetails = cartDetailsRepository
        .findById(new CartDetailsId(cartDetailsRequest.getCartId(), cartDetailsRequest.getGoodsId()))
        .orElseThrow(() -> new IllegalArgumentException("Goods has not been added to the cart"));
    cartDetailsRepository.delete(cartDetails);
    return new DataResponse(cartService.deleteCartDetails(cartDetails));
  }
}
