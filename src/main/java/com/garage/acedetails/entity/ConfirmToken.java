package com.garage.acedetails.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfirmToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String token;
  private LocalDateTime createAt;
  private LocalDateTime expireAt;
  private LocalDateTime confirmAt;
  
  @ManyToOne
  @JoinColumn(nullable = false)
  private Account account;

  public ConfirmToken(String token, LocalDateTime createAt, LocalDateTime expireAt,
      Account account) {
    this.token = token;
    this.createAt = createAt;
    this.expireAt = expireAt;
    this.account = account;
  }  
}
