package com.garage.acedetails.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.AccountDto;
import com.garage.acedetails.entity.Account;
import com.garage.acedetails.entity.ConfirmToken;
import com.garage.acedetails.mapper.AccountMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.AccountRepository;
import com.garage.acedetails.service.AccountService;
import com.garage.acedetails.service.ConfirmTokenService;
import com.garage.acedetails.service.EmailService;
import com.garage.acedetails.util.UserRole;
import com.garage.acedetails.util.UserStatus;

@Service
public class AccountServiceImpl implements AccountService {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AccountMapper accountMapper;
  @Autowired
  private ConfirmTokenService confirmTokenService;
  @Autowired
  private EmailService emailService;

  @Override
  public List<Account> findAll() {
    return null;
  }

  @Override
  public DataResponse insertAccount(AccountDto accountDto, UserRole accountRole) {
    if (accountDto.getPassword().equals(accountDto.getConfirmPassword()) == true) {
      if (accountRepository.findAccountByUsername(accountDto.getUsername()).isPresent()) {
        throw new RuntimeException(ApplicationConstants.USERNAME_IS_DUPLICATED);
      }
      if (accountRepository.findByEmail(accountDto.getEmail()).isPresent()) {
        throw new RuntimeException(ApplicationConstants.EMAIL_IS_DUPLICATED);
      }
      // Encode password by PasswordEncoder of spring security
      String encoderPassword = passwordEncoder.encode(accountDto.getPassword());
      accountDto.setPassword(encoderPassword);
      Account account = accountMapper.accountDtoToEntity(accountDto);
      account.setRole(accountRole);
      Account currentAccount = accountRepository.save(account);
      //After creating an account, create a confirm token then send to user's email
      ConfirmToken confirmToken = confirmTokenService.createConfirmToken(currentAccount);
      emailService.sendRegisterConfirmationToken(confirmToken);
      
      return new DataResponse(accountMapper.entityToAccountSimpleInfoDto(currentAccount));
    } else {
      throw new RuntimeException("CONFIRM_PASSWORD_DOES_NOT_MATCH");
    }
  }

  @Override
  @Transactional
  public DataResponse confirmAccount(String token) {
    ConfirmToken confirmToken = confirmTokenService.findByToken(token);
    //Check if this email has been confirmed
    if(confirmToken.getConfirmAt() != null) {
      throw new RuntimeException(ApplicationConstants.EMAIL_HAS_BEEN_CONFIRMED);
    }
    LocalDateTime now = LocalDateTime.now();
    //Check if token expires
    if(now.isAfter(confirmToken.getExpireAt())) {
      throw new RuntimeException(ApplicationConstants.TOKEN_EXPIRED);
    }
    confirmToken.setConfirmAt(now);
    Account account = confirmToken.getAccount();
    account.setStatus(UserStatus.ACTIVE);
    return new DataResponse(accountMapper.entityToAccountSimpleInfoDto(account));
  }
}
