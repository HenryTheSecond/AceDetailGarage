package com.garage.acedetails.security.model;

import com.garage.acedetails.entity.Account;
import com.garage.acedetails.util.UserStatus;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

  private static final long serialVersionUID = 1L;
  
  private Account account;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(account.getRole().name()));
  }

  @Override
  public String getPassword() {
    return account.getPassword();
  }

  @Override
  public String getUsername() {
    return account.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return account.getStatus().equals(UserStatus.ACTIVE);
  }
}
