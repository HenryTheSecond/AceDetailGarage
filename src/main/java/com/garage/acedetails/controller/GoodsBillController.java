package com.garage.acedetails.controller;

import com.garage.acedetails.dto.goodsbill.GoodsBillRequest;
import com.garage.acedetails.dto.goodsbill.GoodsBillSearchRequest;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.GoodsBillService;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.garage.acedetails.dto.goodsbill.GoodsBillDto;


@RestController
@RequestMapping("/goods-bill")
public class GoodsBillController {
  @Autowired
  private GoodsBillService goodsBillService;

  @GetMapping("/find")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
  public DataResponse getAllGoodsBills(@RequestParam(value = "page", defaultValue = "1") int page,
      @RequestBody(required = false) GoodsBillSearchRequest goodsBillSearchRequest) {
    return goodsBillService.getGoodsBills(page, goodsBillSearchRequest);
  }

  @PostMapping("/add")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public DataResponse insertGoodsBill(@Valid @RequestBody GoodsBillRequest goodsBillRequest) {
    return goodsBillService.insertGoodsBill(goodsBillRequest);
  }

  @PutMapping("/update/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public DataResponse updateGoodsBill(@PathVariable long id, @Valid @RequestBody GoodsBillRequest goodsBillRequest) {
    return goodsBillService.updateGoodsBill(id, goodsBillRequest);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public DataResponse deleteGoodsBill(@PathVariable long id) {
    return goodsBillService.deleteGoodsBill(id);
  }
  
  @GetMapping("/export")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void exportToExcel(@RequestParam(name = "from", required = false) String strFrom,
      @RequestParam(name = "to", required = false) String strTo, HttpServletResponse response) throws IOException {
    goodsBillService.exportToExcel(strFrom, strTo, response);
  }

  @PostMapping("/import")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse buyGoods(@Valid @RequestPart("goodsBill") GoodsBillDto goodsBillDto,
      @RequestParam("listGoods") MultipartFile listGoodsFile) throws IOException, NoSuchFieldException,
      InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ParseException {
    return goodsBillService.purchaseGoodsByExcelFile(goodsBillDto, listGoodsFile);
  }
}
