package com.garage.acedetails.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT(19)")
	private Long id;

	@Column(name = "first_name", columnDefinition = "NVARCHAR(50)", nullable = false)
	@ExcelColumnIndex(index = 1)
	@NotBlank
	@Length(max = 50)
	private String firstName;

	@Column(name = "last_name", columnDefinition = "NVARCHAR(50)")
	@ExcelColumnIndex(index = 2)
	@NotBlank
	@Length(max = 50)
	private String lastName;

	@Column(name = "address", columnDefinition = "NVARCHAR(255)")
	@ExcelColumnIndex(index = 3)
	@NotBlank
	@Length(max = 255)
	private String address;

	@Column(name = "phone", columnDefinition = "VARCHAR(12)", nullable = false, unique = true)
	@ExcelColumnIndex(index = 4)
	@NotBlank
	@Length(max = 12)
	private String phone;

	@Column(name = "salary", columnDefinition = "DOUBLE", nullable = false)
	@ExcelColumnIndex(index = 5)
	@Min(0)
	private double salary;

	@Column(name = "allowance", columnDefinition = "DOUBLE")
	@ExcelColumnIndex(index = 6)
	@Min(0)
	private double allowance;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", unique = true)
	@JsonIgnore
	private Account account;
}
