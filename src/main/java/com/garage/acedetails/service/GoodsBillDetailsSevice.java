package com.garage.acedetails.service;

import com.garage.acedetails.dto.GoodBillDetailsDTO;
import com.garage.acedetails.model.DataResponse;

public interface GoodsBillDetailsSevice {
  DataResponse findAll(int page);

  DataResponse findById(String StrGoodsNumber, String StrBillNumber);

  DataResponse findByGoodsBill(String billID, int page);

  DataResponse insertGoodsBillDetails(GoodBillDetailsDTO billDetailsDTO, int page);

  DataResponse updateGoodsBillDetails(String StrGoodsNumber, String StrBillNumber, GoodBillDetailsDTO billDetailsDTO);

  // DataResponse deleteGoodBillDetalis(String StrGoodsNumber, String StrBillNumber);
}
