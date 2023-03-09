package com.garage.acedetails.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.util.ExcelUtil;

@RestController
@RequestMapping("")
public class CreateMockDataController {
  @Autowired
  private ExcelUtil excelUtil;

  @GetMapping("/create-data")
  public DataResponse createData() throws IOException, NoSuchFieldException, InvocationTargetException,
      NoSuchMethodException, InstantiationException, IllegalAccessException, ParseException {
    return new DataResponse(excelUtil.insertDataToDatabase());
  }
}
