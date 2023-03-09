package com.garage.acedetails.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

	@NotBlank
	@Length(max = 50)
	private String firstName;

	@NotBlank
	@Length(max = 50)
	private String lastName;

	@NotBlank
	@Length(max = 255)
	private String address;

	@NotBlank
	@Length(max = 12)
	private String phone;

	@Min(0)
	private double salary;

	@Min(0)
	private double allowance;
}
