package com.garage.acedetails.dto;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
  private String username;
  private String password;
  private String confirmPassword;
  @Email
  private String email;
}
