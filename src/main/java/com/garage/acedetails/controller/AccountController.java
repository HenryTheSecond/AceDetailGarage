package com.garage.acedetails.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.dto.AccountDto;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.AccountService;
import com.garage.acedetails.util.UserRole;

@RequestMapping("/account")
@RestController
@Validated
public class AccountController {
  @Autowired
  private AccountService accountService;

  @PostMapping("/register")
  // Everyone has the right to add users
  public DataResponse insertUser(@RequestBody @Valid AccountDto accountDto) {
    return accountService.insertAccount(accountDto, UserRole.ROLE_USER);

  }

  @PostMapping("/add-employee")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
  public DataResponse insertEmployee(@RequestBody AccountDto accountDto) {
    return accountService.insertAccount(accountDto, UserRole.ROLE_EMPLOYEE);
  }

  @PostMapping("/add-admin")
  // '//' for the below code to add admin account to test if you want
  // @PreAuthorize("hasRole('ROLE_ADMIN')")
  public DataResponse insertAdmin(@RequestBody AccountDto accountDto) {
    return accountService.insertAccount(accountDto, UserRole.ROLE_ADMIN);

  }

  @GetMapping("/confirm")
  public DataResponse confirmAccount(@RequestParam("token") String token) {
    return accountService.confirmAccount(token);
  }
}
