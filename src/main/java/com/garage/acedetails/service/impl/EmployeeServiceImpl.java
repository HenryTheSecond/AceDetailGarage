package com.garage.acedetails.service.impl;

import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.EmployeeAccountDTO;
import com.garage.acedetails.dto.EmployeeDTO;
import com.garage.acedetails.dto.EmployeeSearchRequest;
import com.garage.acedetails.entity.Account;
import com.garage.acedetails.entity.Employee;
import com.garage.acedetails.mapper.EmployeeMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.AccountRepository;
import com.garage.acedetails.repository.EmployeeRepository;
import com.garage.acedetails.service.EmployeeService;
import com.garage.acedetails.specification.EmployeeSpecification;
import com.garage.acedetails.util.UserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public DataResponse findEmployees(int page, EmployeeSearchRequest employeeSearchRequest) {
		page = page > 0 ? (page - 1) : 0;
		Long id = employeeSearchRequest.getId();
		String name = employeeSearchRequest.getName();
		Specification<Employee> conditions = null;
		if (id != null) {
			conditions = Specification.where(EmployeeSpecification.hasId(id));
		}
		if (name != null) {
			conditions = conditions == null ?
					Specification.where(EmployeeSpecification.hasName(name)) :
					conditions.and(EmployeeSpecification.hasName(name));
		}
		List<Employee> listOfEmployees =
				employeeRepository.findAll(conditions, PageRequest.of(page, ApplicationConstants.EMPLOYEE_PAGE_SIZE))
						.getContent();
		return listOfEmployees.size() > 0 ? new DataResponse(listOfEmployees) : DataResponse.NOT_FOUND;
	}

	@Override
	public DataResponse deleteEmployee(Long id) {
		if (!employeeRepository.findById(id).isPresent()) {
			throw new RuntimeException(ApplicationConstants.EMPLOYEE_NOT_EXIST);
		}
		employeeRepository.deleteById(id);
		return DataResponse.SUCCESSFUL;
	}

	@Override
	public DataResponse insertEmployee(EmployeeDTO employeeDTO) {
		Employee employeeToInsert = employeeMapper.EmployeeDTOToEmployee(employeeDTO);
		if (checkIfPhoneExisted(employeeToInsert.getPhone())) {
			return new DataResponse(
					ApplicationConstants.FAILED,
					ApplicationConstants.PHONE_EXISTED,
					ApplicationConstants.FAILED_CODE
			);
		}
		return Optional.ofNullable(employeeRepository.save(employeeToInsert))
				.map(DataResponse::new)
				.orElseGet(() -> DataResponse.FAILED);
	}

	@Override
	public DataResponse updateEmployee(long id, EmployeeDTO employeeDTO) {
		Employee employeeToUpdate = employeeMapper.EmployeeDTOToEmployee(employeeDTO);
		String employeePhone = employeeToUpdate.getPhone();
		Employee currentEmployee = employeeRepository.findById(id).orElseThrow(
				() -> new EmptyResultDataAccessException(String.format("No employee with id %d exists!", id), 1));
		currentEmployee.setFirstName(employeeToUpdate.getFirstName());
		currentEmployee.setLastName(employeeToUpdate.getLastName());
		currentEmployee.setAddress(employeeToUpdate.getAddress());
		if (!currentEmployee.getPhone().equals(employeePhone)) {
			if (checkIfPhoneExisted(employeePhone)) {
				return new DataResponse(
						ApplicationConstants.FAILED,
						ApplicationConstants.PHONE_EXISTED,
						ApplicationConstants.FAILED_CODE
				);
			}
			currentEmployee.setPhone(employeePhone);
		}
		currentEmployee.setSalary(employeeToUpdate.getSalary());
		currentEmployee.setAllowance(employeeToUpdate.getAllowance());
		return Optional.ofNullable(employeeRepository.saveAndFlush(currentEmployee))
				.map(DataResponse::new)
				.orElseGet(() -> DataResponse.FAILED);
	}

	@Override
	public DataResponse addAccountToEmployee(EmployeeAccountDTO employeeAccountDTO) {
		long accountId = employeeAccountDTO.getAccountId();
		long employeeId = employeeAccountDTO.getEmployeeId();
		Account account = accountRepository.findById(accountId).orElseThrow(() ->
				new EmptyResultDataAccessException(String.format("No account with id %d exists!", accountId), 1));
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
				new EmptyResultDataAccessException(String.format("No employee with id %d exists!", employeeId), 1));
		if (account.getRole().equals(UserRole.ROLE_EMPLOYEE)) {
			employee.setAccount(account);
			return Optional.ofNullable(employeeRepository.save(employee)).map(employeeAfterUpdate -> DataResponse.SUCCESSFUL)
					.orElseGet(() -> DataResponse.FAILED);
		}
		return new DataResponse(ApplicationConstants.FAILED, ApplicationConstants.ACCOUNT_ROLE_INVALID,
				ApplicationConstants.FAILED_CODE);
	}

	@Override
	public DataResponse removeAccountFromEmployee(EmployeeAccountDTO employeeAccountDTO) {
		long accountId = employeeAccountDTO.getAccountId();
		long employeeId = employeeAccountDTO.getEmployeeId();
		Account account = accountRepository.findById(accountId).orElseThrow(() ->
				new EmptyResultDataAccessException(String.format("No account with id %d exists!", accountId), 1));
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
				new EmptyResultDataAccessException(String.format("No employee with id %d exists!", employeeId), 1));
		if (employee.getAccount().getId() == accountId) {
			employee.setAccount(null);
			return Optional.ofNullable(employeeRepository.save(employee)).map(employeeAfterUpdate -> DataResponse.SUCCESSFUL)
					.orElseGet(() -> DataResponse.FAILED);
		}
		return new DataResponse(ApplicationConstants.FAILED, ApplicationConstants.ACCOUNT_ID_INVALID,
				ApplicationConstants.FAILED_CODE);
	}

	private boolean checkIfPhoneExisted(String phone) {
		return employeeRepository.findByPhone(phone).isPresent();
	}
}
