package com.garage.acedetails.entity;


import com.garage.acedetails.util.UserRole;
import com.garage.acedetails.util.UserStatus;
import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Account implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "BIGINT(19)")
  private Long id;

  @Column(name = "username", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
  @ExcelColumnIndex(index = 1)
  private String username;

  @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
  @ExcelColumnIndex(index = 2)
  private String password;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  @ExcelColumnIndex(index = 3)
  private UserRole role;
  
  @Column(name = "email", unique = true)
  @Email
  private String email;
  
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private UserStatus status = UserStatus.INACTIVE;
}
