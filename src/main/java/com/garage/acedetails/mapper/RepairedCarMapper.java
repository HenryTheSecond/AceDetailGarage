package com.garage.acedetails.mapper;

import com.garage.acedetails.dto.repairedcar.RepairedCarAddDto;
import com.garage.acedetails.dto.repairedcar.RepairedCarAndCarCareDto;
import com.garage.acedetails.dto.repairedcar.RepairedCarAndCustomerDto;
import com.garage.acedetails.dto.repairedcar.RepairedCarDto;
import com.garage.acedetails.entity.RepairedCar;

public interface RepairedCarMapper {

  RepairedCarDto entityToRepairedCarDto(RepairedCar repairedCar);

  RepairedCar repairedCarDtoToEntity(RepairedCarDto repairedCarDto);

  RepairedCarAndCustomerDto entityToRepairedCarAndCustomerDto(RepairedCar repairedCar);

  RepairedCar repairedCarAndCustomerDtoToEntity(RepairedCarAndCustomerDto repairedCarAndCustomerDto);

  RepairedCarAddDto entityToRepairedCarAddDto(RepairedCar repairedCar);

  RepairedCar repairedCarAddDtoToEntity(RepairedCarAddDto repairedCarAddDto);

  RepairedCarAndCarCareDto entityToRepairedCarAndCarCareDto(RepairedCar repairedCar);

}
