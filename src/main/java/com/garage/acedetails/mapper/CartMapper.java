package com.garage.acedetails.mapper;

import org.mapstruct.Mapper;
import com.garage.acedetails.dto.CartDto;
import com.garage.acedetails.entity.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
  CartDto entityToCartDto(Cart cart);

  Cart cartDtoToEntity(CartDto cartDto);
}
