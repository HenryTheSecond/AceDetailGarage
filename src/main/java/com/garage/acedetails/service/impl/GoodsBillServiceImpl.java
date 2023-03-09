package com.garage.acedetails.service.impl;

import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.goodsbill.GoodsBillRequest;
import com.garage.acedetails.dto.goodsbill.GoodsBillResponse;
import com.garage.acedetails.dto.goodsbill.GoodsBillSearchRequest;
import com.garage.acedetails.entity.Customer;
import com.garage.acedetails.entity.GoodBillDetails;
import com.garage.acedetails.entity.GoodsBill;
import com.garage.acedetails.mapper.GoodsBillMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.CustomerRepository;
import com.garage.acedetails.repository.GoodsBillRepository;
import com.garage.acedetails.service.EmailService;
import com.garage.acedetails.service.GoodsBillService;
import com.garage.acedetails.specification.GoodsBillSpecification;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.garage.acedetails.dto.GoodsBillDetailsDto;
import com.garage.acedetails.dto.goodsbill.GoodsBillDto;
import com.garage.acedetails.util.ExcelUtil;

@Service
public class GoodsBillServiceImpl implements GoodsBillService {
  @Autowired
  private GoodsBillRepository goodsBillRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private GoodsBillMapper goodsBillMapper;
  @Autowired
  private ExcelUtil excelUtil;
  @Autowired
  private EmailService emailService;

  @Override
  public DataResponse getGoodsBills(int page, GoodsBillSearchRequest goodsBillSearchRequest) {
    page = page > 0 ? (page - 1) : 0;
    Specification<GoodsBill> conditions = null;
    Sort sort = Sort.unsorted();
    if (goodsBillSearchRequest != null) {
      Long goodsBillId = goodsBillSearchRequest.getGoodsBillId();
      Long customerId = goodsBillSearchRequest.getCustomerId();
      String fromPurchaseDate = goodsBillSearchRequest.getFromPurchaseDate();
      String toPurchaseDate = goodsBillSearchRequest.getToPurchaseDate();
      String orderByProperties = goodsBillSearchRequest.getOrderByProperties();
      String orderType = goodsBillSearchRequest.getOrderType();
      conditions = createSpecification(goodsBillId, customerId, fromPurchaseDate, toPurchaseDate);
      sort = createSort(orderByProperties, orderType);
    }
    List<GoodsBillResponse> listOfGoodsBillDTO =
        goodsBillRepository.findAll(conditions, PageRequest.of(page, ApplicationConstants.GOODS_BILL_PAGE_SIZE, sort))
            .getContent().stream().map(goodsBillMapper::GoodsBillToGoodsBillResponse).collect(Collectors.toList());
    return listOfGoodsBillDTO.size() > 0 ? new DataResponse(listOfGoodsBillDTO) : DataResponse.NOT_FOUND;
  }

  @Override
  public DataResponse insertGoodsBill(GoodsBillRequest goodsBillDTO) {
    GoodsBill goodsBill = goodsBillRepository.save(goodsBillMapper.GoodsBillRequestToGoodsBill(goodsBillDTO));
    return goodsBill != null ? new DataResponse(goodsBill) : DataResponse.FAILED;
  }

  @Override
  public DataResponse updateGoodsBill(Long id, GoodsBillRequest goodsBillRequest) {
    GoodsBill goodsBillToUpdate = goodsBillMapper.GoodsBillRequestToGoodsBill(goodsBillRequest);
    GoodsBill currentGoodsBill = goodsBillRepository.findById(id)
        .orElseThrow(() -> new EmptyResultDataAccessException(ApplicationConstants.GOODS_BILL_NOT_FOUND, 1));
    currentGoodsBill.setTotal(goodsBillToUpdate.getTotal());
    currentGoodsBill.setPaymentMethod(goodsBillToUpdate.getPaymentMethod());
    currentGoodsBill.setPurchaseDate(goodsBillToUpdate.getPurchaseDate());
    currentGoodsBill.setPaymentDate(goodsBillToUpdate.getPaymentDate());
    currentGoodsBill.setCustomer(goodsBillToUpdate.getCustomer());
    GoodsBill goodsBillAfterUpdate = goodsBillRepository.save(currentGoodsBill);
    return goodsBillAfterUpdate != null ? new DataResponse(goodsBillAfterUpdate) : DataResponse.SUCCESSFUL;
  }

  @Override
  public DataResponse deleteGoodsBill(Long id) {
    goodsBillRepository.deleteById(id);
    return DataResponse.SUCCESSFUL;
  }

  public Specification<GoodsBill> createSpecification(Long goodsBillId, Long customerId, String fromPurchaseDate,
      String toPurchaseDate) {
    Specification<GoodsBill> conditions = null;
    List<Specification<GoodsBill>> listOfSpecifications = new ArrayList<>();
    if (goodsBillId != null) {
      listOfSpecifications.add(GoodsBillSpecification.hasId(goodsBillId));
    }
    if (customerId != null) {
      Customer customer = customerRepository.findById(customerId)
          .orElseThrow(() -> new RuntimeException(ApplicationConstants.CUSTOMER_NOT_FOUND));
      listOfSpecifications.add(GoodsBillSpecification.hasCustomer(customer));
    }
    if (fromPurchaseDate != null) {
      LocalDate date = LocalDate.parse(fromPurchaseDate);
      listOfSpecifications.add(GoodsBillSpecification.fromPurchaseDate(date));
    }
    if (toPurchaseDate != null) {
      LocalDate date = LocalDate.parse(toPurchaseDate);
      listOfSpecifications.add(GoodsBillSpecification.toPurchaseDate(date));
    }
    int size = listOfSpecifications.size();
    if (size > 0) {
      conditions = Specification.where(listOfSpecifications.get(0));
      for (int i = 1; i < size; i++) {
        conditions = conditions.and(listOfSpecifications.get(i));
      }
    }
    return conditions;
  }

  public Sort createSort(String listOfOrderBy, String orderType) {
    Sort sort = Sort.unsorted();
    if (listOfOrderBy != null) {
      Set<String> setOfOrderBy = new HashSet<>(Arrays.asList(listOfOrderBy.split(",")));
      String orderByProperty;
      List<String> listOfFieldsName =
          Arrays.stream(GoodsBill.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
      Iterator<String> iterator = setOfOrderBy.iterator();
      while (iterator.hasNext()) {
        orderByProperty = iterator.next();
        if (!listOfFieldsName.contains(orderByProperty)) {
          iterator.remove();
        }
      }
      sort = Sort.by(setOfOrderBy.toArray(new String[0])).ascending();
      if (orderType != null && orderType.toUpperCase().trim().equals(ApplicationConstants.ORDER_DESCENDING)) {
        sort = sort.descending();
      }
    }
    return sort;
  }

  @Override
  public void exportToExcel(String strFrom, String strTo, HttpServletResponse response) throws IOException {
    LocalDate from = null;
    LocalDate to = null;
    String fileName = "Statistic";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      if (strFrom != null) {
        from = LocalDate.parse(strFrom, formatter);
        fileName += "_from_" + from; 
      }
      if (strTo != null) {
        to = LocalDate.parse(strTo, formatter);
        fileName += "_to_" + to;
      }
    } catch (DateTimeParseException e) {
      e.printStackTrace();
      throw new RuntimeException(ApplicationConstants.PARAMS_INPUT_INVALID);
    }
    List<GoodsBill> list = goodsBillRepository.findByPurchaseDate(from, to);
    Workbook workbook = writeToWorkBook(list);
    
    response.setContentType("application/octet-stream");
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".xlsx");
    ServletOutputStream outputStream = response.getOutputStream();
    workbook.write(outputStream);
    workbook.close();
    outputStream.close();
  }

  private Workbook writeToWorkBook(List<GoodsBill> list) {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet();
    Row row = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    row = sheet.createRow(0);
    row.createCell(0).setCellValue("ID");
    row.createCell(1).setCellValue("Payment method");
    row.createCell(2).setCellValue("Total");
    row.createCell(3).setCellValue("Purchase date");
    row.createCell(4).setCellValue("Payment date");
    row.createCell(5).setCellValue("Customer Id");
    row.createCell(6).setCellValue("Customer name");
    row.createCell(7).setCellValue("Detail");
    int i = 1;
    CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
    cellStyle.setWrapText(true);
    
    for (GoodsBill goodsBill : list) {
      row = sheet.createRow(i);

      row.createCell(0).setCellValue(goodsBill.getId());
      row.createCell(1).setCellValue(goodsBill.getPaymentMethod().name());
      row.createCell(2).setCellValue(goodsBill.getTotal());
      row.createCell(3).setCellValue(goodsBill.getPurchaseDate().format(formatter));
      row.createCell(4)
          .setCellValue(goodsBill.getPaymentDate() == null ? "Unpaid" : goodsBill.getPaymentDate().format(formatter));
      row.createCell(5).setCellValue(goodsBill.getCustomer().getId());
      row.createCell(6)
          .setCellValue(goodsBill.getCustomer().getFirstName() + " " + goodsBill.getCustomer().getLastName());
      row.createCell(7).setCellValue(stringifyGoodsBillDetails(goodsBill.getSetOfGoodsBillDetail()));

      row.setHeightInPoints(sheet.getDefaultRowHeightInPoints() * (goodsBill.getSetOfGoodsBillDetail().size() + 1));
    
      row.getCell(7).setCellStyle(cellStyle);

      i++;
    }    
    return workbook;
  }

  private String stringifyGoodsBillDetails(Set<GoodBillDetails> setOfGoodsBillDetail) {
    String result = "";
    for (GoodBillDetails detail : setOfGoodsBillDetail) {
      result +=
          String.format("- %s: %s units, price %s, total %s\n", detail.getGoodBillDetailsPK().getGoods().getName(),
              detail.getQuantity(), detail.getPrice(), detail.getTotalPrice());
    }
    return result;
  }

  public DataResponse purchaseGoodsByExcelFile(GoodsBillDto goodsBillDto, MultipartFile listGoodsFile)
      throws IOException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, ParseException {
    if (listGoodsFile.getOriginalFilename().endsWith(".xlsx")) {
      // insert GoodsBill from goodsBillDto
      GoodsBill goodsBill = goodsBillMapper.GoodsBillDtoToGoodsBill(goodsBillDto);
      goodsBill = goodsBillRepository.save(goodsBill);
      // get list of goods to buy using excel file
      List<GoodsBillDetailsDto> listOfGoodsBillDetailsDto = excelUtil.getListObjectFromExcelFile(
          GoodsBillDetailsDto.class, ApplicationConstants.GOODS_BILL_DETAILS_SHEET, listGoodsFile);
      // save to GoodsBillDetails database (Trinh)
      return new DataResponse(listOfGoodsBillDetailsDto);
    } else {
      return new DataResponse(ApplicationConstants.WRONG_FORMAT, ApplicationConstants.WRONG_FORMAT_MESSAGE, null, 200);
    }
  }

  @Override
  @Transactional // For getting lazy attribute
  public void weeklyGoodsBillStatistics() {
    LocalDate now = LocalDate.now();
    List<GoodsBill> list = goodsBillRepository.findByPurchaseDate(now.minusWeeks(1), now);
    Workbook workbook = writeToWorkBook(list);
    emailService.sendWeeklyGoodsBillStatistics(workbook, now);
  }
}
