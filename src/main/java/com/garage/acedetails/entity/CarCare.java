package com.garage.acedetails.entity;

import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class CarCare {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long number;

  @NotNull(message = "Service name must not be null")
  @NotBlank(message = "Service name must not be blank")
  @Column(name = "service_name", nullable = false, unique = true)
  @ExcelColumnIndex(index = 1)
  private String serviceName;

  @ExcelColumnIndex(index = 2)
  private double price;
  @ExcelColumnIndex(index = 3)
  private String description;

  @OneToMany(mappedBy = "carCare", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<UsedService> setOfUsedService;



  public CarCare(@NotNull(message = "Service name must not be null") @NotBlank(
      message = "Service name must not be blank") String serviceName, double price, String description) {
    super();
    this.serviceName = serviceName;
    this.price = price;
    this.description = description;
  }


}
