package com.garage.acedetails.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.dto.BillDto;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.BillService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/bill")
@RestController
@RequiredArgsConstructor
public class BillController {
  @Autowired
  private final BillService billService;

  @GetMapping("/get/{id}")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
  public DataResponse getBillById(@PathVariable(value = "id") String rawId) {
    return billService.findById(rawId);
  }

  @GetMapping("/get-all")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
  public DataResponse getBillByPage(Pageable pageable) {
    return billService.findAll(pageable);
  }

  @PostMapping("/add")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
  public DataResponse addBill(@Valid @RequestBody BillDto billDto) {
    return billService.insertBill(billDto);
  }


  @PostMapping("/update/")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public DataResponse updateBill(@Valid @RequestBody BillDto billDto) {
    return billService.updateBill(billDto);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public DataResponse deleteBill(@PathVariable(value = "id") String rawId) {
    return billService.deleteBill(rawId);
  }
}
