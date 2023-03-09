package com.garage.acedetails.mapper;

import org.mapstruct.Mapper;
import com.garage.acedetails.dto.carcare.CarCareDto;
import com.garage.acedetails.entity.CarCare;

@Mapper(componentModel = "spring")
public interface CarCareMapper {

  CarCareDto entityToCarCareDto(CarCare carCare);

  CarCare carCareDtoToEntity(CarCareDto carCareDto);
}
