package com.garage.acedetails.service;

import org.springframework.data.domain.Pageable;
import com.garage.acedetails.dto.BillDto;
import com.garage.acedetails.model.DataResponse;

public interface BillService {

  DataResponse findById(String rawId);

  DataResponse findAll(Pageable pageable);

  DataResponse insertBill(BillDto billDto);

  DataResponse updateBill(BillDto billDto);

  DataResponse deleteBill(String rawId);
}
