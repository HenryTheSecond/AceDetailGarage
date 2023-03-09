package com.garage.acedetails.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.BillDto;
import com.garage.acedetails.entity.Bill;
import com.garage.acedetails.mapper.BillMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.BillRepository;
import com.garage.acedetails.service.BillService;

@Service
public class BillServiceImpl implements BillService {

  @Autowired
  private BillRepository billRepository;

  @Autowired
  private BillMapper billMapper;

  @Override
  public DataResponse findById(String rawId) {
    Long id = Long.parseLong(rawId);
    Bill bill =
        billRepository.findById(id).orElseThrow(() -> new RuntimeException(ApplicationConstants.BILL_NOT_FOUND));
    BillDto billDto = billMapper.entityToBillAndRepairedCarDto(bill);
    return new DataResponse(billDto);
  }

  @Override
  public DataResponse findAll(Pageable pageable) {
    return new DataResponse(billRepository.findAll(pageable).getContent());
  }

  @Override
  public DataResponse insertBill(BillDto billDto) {
    return new DataResponse(
        billMapper.entityToBillAndRepairedCarDto(billRepository.save(billMapper.BillDtoToEntity(billDto))));
  }

  @Override
  public DataResponse updateBill(BillDto billDto) {
    billRepository.findById(billDto.getNumber())
        .orElseThrow(() -> new RuntimeException(ApplicationConstants.BILL_NOT_FOUND));
    return new DataResponse(
        billMapper.entityToBillAndRepairedCarDto(billRepository.save(billMapper.BillDtoToEntity(billDto))));
  }

  @Override
  public DataResponse deleteBill(String rawId) {
    try {
      billRepository.deleteById(Long.parseLong(rawId));
      return DataResponse.SUCCESSFUL;
    } catch (EmptyResultDataAccessException e) {
      return DataResponse.BAD_REQUEST;
    }
  }
}
