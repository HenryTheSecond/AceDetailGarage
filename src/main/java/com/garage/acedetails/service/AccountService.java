package com.garage.acedetails.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.garage.acedetails.dto.AccountDto;
import com.garage.acedetails.entity.Account;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.util.UserRole;

@Service
public interface AccountService {
  public List<Account> findAll();
  DataResponse confirmAccount(String token);
  DataResponse insertAccount(AccountDto accountDto, UserRole accountRole);
}
