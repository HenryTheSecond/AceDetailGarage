package com.garage.acedetails.service;

import java.time.LocalDate;
import org.apache.poi.ss.usermodel.Workbook;
import com.garage.acedetails.entity.ConfirmToken;

public interface EmailService {
  void sendRegisterConfirmationToken(ConfirmToken confirmToken);
  void sendWeeklyGoodsBillStatistics(Workbook workbook, LocalDate date);
}
