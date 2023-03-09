package com.garage.acedetails.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.BillAndRepairedCarDto;
import com.garage.acedetails.dto.BillDto;
import com.garage.acedetails.entity.Bill;
import com.garage.acedetails.entity.RepairedCar;
import com.garage.acedetails.mapper.BillMapper;
import com.garage.acedetails.mapper.RepairedCarMapper;
import com.garage.acedetails.repository.RepairedCarRepository;

@Component
public class BillMapperImpl implements BillMapper {

  @Autowired
  private RepairedCarRepository repairedCarRepository;

  @Autowired
  private RepairedCarMapper repairedCarMapper;

  @Override
  public BillDto entityToBillDto(Bill bill) {
    if (bill == null) {
      return null;
    }

    BillDto billDto = new BillDto();
    billDto.setNumber(bill.getNumber());
    billDto.setPaymentMethod(bill.getPaymentMethod());
    billDto.setTotalMoney(bill.getTotalMoney());
    billDto.setPaymentDate(bill.getPaymentDate());
    billDto.setRepairedCarId(bill.getRepairedCar().getId());

    return billDto;
  }

  @Override
  public Bill BillDtoToEntity(BillDto billDto) {
    if (billDto == null) {
      return null;
    }

    Bill bill = new Bill();
    bill.setNumber(billDto.getNumber());
    bill.setPaymentMethod(billDto.getPaymentMethod());
    bill.setTotalMoney(billDto.getTotalMoney());
    bill.setPaymentDate(billDto.getPaymentDate());
    RepairedCar repairedCar = repairedCarRepository.findById(billDto.getRepairedCarId())
        .orElseThrow(() -> new RuntimeException(ApplicationConstants.CAR_NOT_FOUND));
    if (repairedCar.getBill() != null) {
      return null;
    }
    bill.setRepairedCar(repairedCar);

    return bill;
  }

  @Override
  public BillAndRepairedCarDto entityToBillAndRepairedCarDto(Bill bill) {
    if (bill == null) {
      return null;
    }

    BillAndRepairedCarDto billAndRepairedCarDto = new BillAndRepairedCarDto();
    billAndRepairedCarDto.setNumber(bill.getNumber());
    billAndRepairedCarDto.setPaymentMethod(bill.getPaymentMethod());
    billAndRepairedCarDto.setTotalMoney(bill.getTotalMoney());
    billAndRepairedCarDto.setPaymentDate(bill.getPaymentDate());
    billAndRepairedCarDto.setRepairedCarId(bill.getRepairedCar().getId());
    billAndRepairedCarDto.setRepairedCarDto(repairedCarMapper.entityToRepairedCarDto(bill.getRepairedCar()));

    return billAndRepairedCarDto;
  }

  @Override
  public Bill BillAndRepairedCarDtoToEntity(BillAndRepairedCarDto billAndRepairedCarDto) {
    if (billAndRepairedCarDto == null) {
      return null;
    }

    Bill bill = new Bill();
    bill.setNumber(billAndRepairedCarDto.getNumber());
    bill.setPaymentMethod(billAndRepairedCarDto.getPaymentMethod());
    bill.setTotalMoney(billAndRepairedCarDto.getTotalMoney());
    bill.setPaymentDate(billAndRepairedCarDto.getPaymentDate());
    bill.setRepairedCar(repairedCarMapper.repairedCarDtoToEntity(billAndRepairedCarDto.getRepairedCarDto()));

    return bill;
  }
}
