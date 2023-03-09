package com.garage.acedetails.controller;

import com.garage.acedetails.dto.EmployeeAccountDTO;
import com.garage.acedetails.dto.EmployeeDTO;
import com.garage.acedetails.dto.EmployeeSearchRequest;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.EmployeeService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/find")
	@PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
	public DataResponse getEmployees(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestBody EmployeeSearchRequest employeeSearchRequest) {
		return employeeService.findEmployees(page, employeeSearchRequest);
	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public DataResponse insertEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		return employeeService.insertEmployee(employeeDTO);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public DataResponse updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, @PathVariable Long id) {
		return employeeService.updateEmployee(id, employeeDTO);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public DataResponse deleteEmployee(@PathVariable Long id) {
		return employeeService.deleteEmployee(id);
	}

	@PostMapping("/add-account")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public DataResponse addAccountToEmployee(@RequestBody EmployeeAccountDTO employeeAccountDTO) {
		return employeeService.addAccountToEmployee(employeeAccountDTO);
	}

	@PostMapping("/remove-account")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public DataResponse removeAccountToEmployee(@RequestBody EmployeeAccountDTO employeeAccountDTO) {
		return employeeService.removeAccountFromEmployee(employeeAccountDTO);
	}
}
