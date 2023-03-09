package com.garage.acedetails.entity;

import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "customer")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "BIGINT(19)")
  private Long id;

  @Column(name = "first_name", columnDefinition = "NVARCHAR(50)", nullable = false)
  @ExcelColumnIndex(index = 1)
  private String firstName;

  @Column(name = "last_name", columnDefinition = "NVARCHAR(50)")
  @ExcelColumnIndex(index = 2)
  private String lastName;

  @Column(name = "address", columnDefinition = "NVARCHAR(255)")
  @ExcelColumnIndex(index = 4)
  private String address;

  @Column(name = "phone", columnDefinition = "VARCHAR(12)", nullable = false, unique = true)
  @ExcelColumnIndex(index = 3)
  private String phone;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<RepairedCar> listRepairedCars;

  @OneToOne()
  @JoinColumn(name = "account_id", nullable = true, unique = true)
  @JsonIgnore
  private Account account;
}
