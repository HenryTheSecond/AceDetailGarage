package com.garage.acedetails.mapper.impl;

import java.util.HashSet;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.garage.acedetails.dto.carcare.CarCareDto;
import com.garage.acedetails.dto.repairedcar.RepairedCarAddDto;
import com.garage.acedetails.dto.repairedcar.RepairedCarAndCarCareDto;
import com.garage.acedetails.dto.repairedcar.RepairedCarAndCustomerDto;
import com.garage.acedetails.dto.repairedcar.RepairedCarDto;
import com.garage.acedetails.entity.CarCare;
import com.garage.acedetails.entity.RepairedCar;
import com.garage.acedetails.entity.UsedService;
import com.garage.acedetails.mapper.CarCareMapper;
import com.garage.acedetails.mapper.CustomerMapper;
import com.garage.acedetails.mapper.RepairedCarMapper;
import com.garage.acedetails.repository.CarCareRepository;

@Component
public class RepairedCarMapperImpl implements RepairedCarMapper {
  @Autowired
  private CustomerMapper customerMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CarCareMapper carCareMapper;

  @Autowired
  private CarCareRepository carCareRepository;


  @Override
  public RepairedCarDto entityToRepairedCarDto(RepairedCar repairedCar) {
    if (repairedCar == null) {
      return null;
    }

    RepairedCarDto repairedCarDto = new RepairedCarDto();

    repairedCarDto.setId(repairedCar.getId());
    repairedCarDto.setStartDate(repairedCar.getStartDate());
    repairedCarDto.setEndDate(repairedCar.getEndDate());
    repairedCarDto.setDescription(repairedCar.getDescription());
    repairedCarDto.setCarType(repairedCar.getCarType());
    repairedCarDto.setCarNumber(repairedCar.getCarNumber());
    repairedCarDto.setCarStatus(repairedCar.getCarStatus());

    return repairedCarDto;
  }

  @Override
  public RepairedCar repairedCarDtoToEntity(RepairedCarDto repairedCarDto) {
    if (repairedCarDto == null) {
      return null;
    }

    RepairedCar repairedCar = new RepairedCar();

    repairedCar.setId(repairedCarDto.getId());
    repairedCar.setStartDate(repairedCarDto.getStartDate());
    repairedCar.setEndDate(repairedCarDto.getEndDate());
    repairedCar.setDescription(repairedCarDto.getDescription());
    repairedCar.setCarType(repairedCarDto.getCarType());
    repairedCar.setCarNumber(repairedCarDto.getCarNumber());
    repairedCar.setCarStatus(repairedCarDto.getCarStatus());

    return repairedCar;
  }

  @Override
  public RepairedCarAndCustomerDto entityToRepairedCarAndCustomerDto(RepairedCar repairedCar) {
    if (repairedCar == null) {
      return null;
    }

    RepairedCarAndCustomerDto repairedCarAndCustomerDto = new RepairedCarAndCustomerDto();

    repairedCarAndCustomerDto.setCarNumber(repairedCar.getCarNumber());
    repairedCarAndCustomerDto.setCarStatus(repairedCar.getCarStatus());
    repairedCarAndCustomerDto.setCarType(repairedCar.getCarType());
    repairedCarAndCustomerDto.setDescription(repairedCar.getDescription());
    repairedCarAndCustomerDto.setEndDate(repairedCar.getEndDate());
    repairedCarAndCustomerDto.setId(repairedCar.getId());
    repairedCarAndCustomerDto.setStartDate(repairedCar.getStartDate());
    repairedCarAndCustomerDto.setCustomer(customerMapper.entityToCustomerRequestDto(repairedCar.getCustomer()));

    return repairedCarAndCustomerDto;
  }

  @Override
  public RepairedCar repairedCarAndCustomerDtoToEntity(RepairedCarAndCustomerDto repairedCarAndCustomerDto) {
    if (repairedCarAndCustomerDto == null) {
      return null;
    }

    RepairedCar repairedCar = new RepairedCar();

    repairedCar.setCarNumber(repairedCarAndCustomerDto.getCarNumber());
    repairedCar.setCarStatus(repairedCarAndCustomerDto.getCarStatus());
    repairedCar.setCarType(repairedCarAndCustomerDto.getCarType());
    repairedCar.setCustomer(customerMapper.customerRequestDtoToEntity(repairedCarAndCustomerDto.getCustomer()));
    repairedCar.setDescription(repairedCarAndCustomerDto.getDescription());
    repairedCar.setEndDate(repairedCarAndCustomerDto.getEndDate());
    repairedCar.setId(repairedCarAndCustomerDto.getId());
    repairedCar.setStartDate(repairedCarAndCustomerDto.getStartDate());

    return repairedCar;
  }

  @Override
  public RepairedCarAddDto entityToRepairedCarAddDto(RepairedCar repairedCar) {
    if (repairedCar == null) {
      return null;
    }

    RepairedCarAddDto repairedCarAddDto = new RepairedCarAddDto();

    repairedCarAddDto.setCarNumber(repairedCar.getCarNumber());
    repairedCarAddDto.setCarStatus(repairedCar.getCarStatus());
    repairedCarAddDto.setCarType(repairedCar.getCarType());
    repairedCarAddDto.setDescription(repairedCar.getDescription());
    repairedCarAddDto.setEndDate(repairedCar.getEndDate());
    repairedCarAddDto.setId(repairedCar.getId());
    repairedCarAddDto.setStartDate(repairedCar.getStartDate());
    repairedCarAddDto.setCustomer(customerMapper.entityToCustomerRequestDto(repairedCar.getCustomer()));

    return repairedCarAddDto;
  }

  @Override
  public RepairedCar repairedCarAddDtoToEntity(RepairedCarAddDto repairedCarAddDto) {
    if (repairedCarAddDto == null) {
      return null;
    }

    RepairedCar repairedCar = new RepairedCar();

    repairedCar.setCarNumber(repairedCarAddDto.getCarNumber());
    repairedCar.setCarStatus(repairedCarAddDto.getCarStatus());
    repairedCar.setCarType(repairedCarAddDto.getCarType());
    repairedCar.setCustomer(customerMapper.customerRequestDtoToEntity(repairedCarAddDto.getCustomer()));
    repairedCar.setDescription(repairedCarAddDto.getDescription());
    repairedCar.setEndDate(repairedCarAddDto.getEndDate());
    repairedCar.setId(repairedCarAddDto.getId());
    repairedCar.setStartDate(repairedCarAddDto.getStartDate());

    return repairedCar;
  }

  @Override
  public RepairedCarAndCarCareDto entityToRepairedCarAndCarCareDto(RepairedCar repairedCar) {
    if (repairedCar == null) {
      return null;
    }
    RepairedCarAndCarCareDto repairedCarAndCarCareDto = modelMapper.map(repairedCar, RepairedCarAndCarCareDto.class);
    Set<CarCareDto> setOfCarCare = new HashSet<>();
    CarCare carCare = null;
    for (UsedService usedService : repairedCar.getSetOfUsedService()) {
      carCare = carCareRepository.findById(usedService.getId().getCarCareNumber()).get();
      setOfCarCare.add(carCareMapper.entityToCarCareDto(carCare));
    }
    repairedCarAndCarCareDto.setSetOfCarCare(setOfCarCare);
    return repairedCarAndCarCareDto;
  }
}
