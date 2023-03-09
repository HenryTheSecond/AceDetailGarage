package com.garage.acedetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAccountDTO {

	private long accountId;
	private long employeeId;
}
