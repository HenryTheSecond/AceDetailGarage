package com.garage.acedetails.mapper;

import com.garage.acedetails.dto.BillAndRepairedCarDto;
import com.garage.acedetails.dto.BillDto;
import com.garage.acedetails.entity.Bill;


public interface BillMapper {
  BillDto entityToBillDto(Bill bill);

  Bill BillDtoToEntity(BillDto billDto);

  BillAndRepairedCarDto entityToBillAndRepairedCarDto(Bill bill);

  Bill BillAndRepairedCarDtoToEntity(BillAndRepairedCarDto BillAndRepairedCarDto);
}
