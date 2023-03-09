package com.garage.acedetails.dto.account;


import javax.validation.constraints.Email;
import com.garage.acedetails.util.UserRole;
import com.garage.acedetails.util.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSimpleInfoDto {
  private Long id;
  private String username;
  private UserRole role;
  @Email
  private String email;
  private UserStatus status;
}
