package com.garage.acedetails.service;

import com.garage.acedetails.dto.EmployeeAccountDTO;
import com.garage.acedetails.dto.EmployeeDTO;
import com.garage.acedetails.dto.EmployeeSearchRequest;
import com.garage.acedetails.model.DataResponse;


public interface EmployeeService {

	DataResponse findEmployees(int page, EmployeeSearchRequest employeeSearchRequest);

	DataResponse deleteEmployee(Long id);

	DataResponse insertEmployee(EmployeeDTO employeeDTO);

	DataResponse updateEmployee(long id, EmployeeDTO employeeDTO);

	DataResponse addAccountToEmployee(EmployeeAccountDTO employeeAccountDTO);

	DataResponse removeAccountFromEmployee(EmployeeAccountDTO employeeAccountDTO);
}
