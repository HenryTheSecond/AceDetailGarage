package com.garage.acedetails.mapper;

import org.mapstruct.Mapper;
import com.garage.acedetails.dto.EmployeeDTO;
import com.garage.acedetails.entity.Employee;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {

  public abstract Employee EmployeeDTOToEmployee(EmployeeDTO employeeDTO);

  public abstract EmployeeDTO EmployeeToEmployeeDTO(Employee employee);
}
