package com.garage.acedetails.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.dto.GoodBillDetailsDTO;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.GoodsBillDetailsSevice;
import com.garage.acedetails.util.ValidId;

@RestController
@RequestMapping("/goods-bills")
public class GoodBillDetailsController {
  @Autowired
  private GoodsBillDetailsSevice goodsbillDetailsSevice;

  @GetMapping("/find-all")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
  public DataResponse findAll(@RequestParam(value = "page", defaultValue = "1") int page) {
    return goodsbillDetailsSevice.findAll(page);
  }

  @GetMapping("/find-by-goodBills/{carId}")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse findByGoodsBill(@PathVariable("id") @ValidId String billID,
      @RequestParam(name = "page", defaultValue = "1") int page) {
    return goodsbillDetailsSevice.findByGoodsBill(billID, page);
  }

  @GetMapping("/find-by-id")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse findById(
      @RequestParam(name = "goods-id", defaultValue = "0") @PathVariable("goodsId") @ValidId String goodsId,
      @RequestParam(name = "bill-id", defaultValue = "0") @PathVariable("billId") @ValidId String billId) {
    return goodsbillDetailsSevice.findById(goodsId, billId);
  }

  @PutMapping("/update")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
  public DataResponse updateGoodsBillDetails(@Valid @RequestBody GoodBillDetailsDTO billDetailsDTO,
      @RequestParam(name = "goods-id", defaultValue = "0") @PathVariable("goodsId") @ValidId String goodsId,
      @RequestParam(name = "bill-id", defaultValue = "0") @PathVariable("billId") @ValidId String billId) {
    return goodsbillDetailsSevice.updateGoodsBillDetails(goodsId, billId, billDetailsDTO);
  }
}
