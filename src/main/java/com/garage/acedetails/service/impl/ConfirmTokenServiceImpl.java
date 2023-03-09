package com.garage.acedetails.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.entity.Account;
import com.garage.acedetails.entity.ConfirmToken;
import com.garage.acedetails.repository.ConfirmTokenRepository;
import com.garage.acedetails.service.ConfirmTokenService;

@Service
public class ConfirmTokenServiceImpl implements ConfirmTokenService {
  @Autowired
  private ConfirmTokenRepository confirmTokenRepository;
  
  @Value("${token.duration}")
  private int CONFIRM_TOKEN_DURATION;
  
  @Override
  public ConfirmToken createConfirmToken(Account account) {
    System.out.println(CONFIRM_TOKEN_DURATION);
    String token = UUID.randomUUID().toString();
    LocalDateTime createAt = LocalDateTime.now();
    LocalDateTime expireAt = createAt.plusMinutes(CONFIRM_TOKEN_DURATION);
    ConfirmToken confirmToken = new ConfirmToken(token, createAt, expireAt, account);
    return confirmTokenRepository.save(confirmToken);
  }

  @Override
  public ConfirmToken findByToken(String token) {
    return confirmTokenRepository.findByToken(token)
        .orElseThrow(() -> new RuntimeException(ApplicationConstants.TOKEN_IS_NOT_FOUND));
  }
}
