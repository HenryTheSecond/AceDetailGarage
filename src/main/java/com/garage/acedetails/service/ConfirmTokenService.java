package com.garage.acedetails.service;

import com.garage.acedetails.entity.Account;
import com.garage.acedetails.entity.ConfirmToken;

public interface ConfirmTokenService {
  ConfirmToken createConfirmToken(Account account);
  ConfirmToken findByToken(String token);
}
