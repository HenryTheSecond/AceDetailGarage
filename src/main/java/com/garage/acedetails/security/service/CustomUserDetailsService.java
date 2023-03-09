package com.garage.acedetails.security.service;

import com.garage.acedetails.entity.Account;
import com.garage.acedetails.repository.AccountRepository;
import com.garage.acedetails.security.model.CustomUserDetails;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Account> optionalAccount = accountRepository.findAccountByUsername(username);
    if (optionalAccount.isPresent()) {
      Account account = optionalAccount.get();
      return new CustomUserDetails(account);
    }
    throw new UsernameNotFoundException(username + " not found");
  }
}
