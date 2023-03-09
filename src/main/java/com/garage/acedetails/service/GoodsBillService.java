package com.garage.acedetails.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import com.garage.acedetails.dto.goodsbill.GoodsBillDto;
import com.garage.acedetails.dto.goodsbill.GoodsBillRequest;
import com.garage.acedetails.dto.goodsbill.GoodsBillSearchRequest;
import com.garage.acedetails.model.DataResponse;

public interface GoodsBillService {
  DataResponse getGoodsBills(int page, GoodsBillSearchRequest goodsBillSearchRequest);

  DataResponse insertGoodsBill(GoodsBillRequest goodsBillDTO);

  DataResponse updateGoodsBill(Long id, GoodsBillRequest goodsBillDTO);

  DataResponse deleteGoodsBill(Long id);

  void exportToExcel(String strFrom, String strTo, HttpServletResponse response) throws IOException;
  
  void weeklyGoodsBillStatistics();

  DataResponse purchaseGoodsByExcelFile(GoodsBillDto goodsBillDto, MultipartFile listGoodsFile)
      throws IOException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, ParseException;
}
