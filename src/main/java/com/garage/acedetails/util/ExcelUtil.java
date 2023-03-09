package com.garage.acedetails.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.entity.Account;
import com.garage.acedetails.entity.Bill;
import com.garage.acedetails.entity.CarCare;
import com.garage.acedetails.entity.Category;
import com.garage.acedetails.entity.Customer;
import com.garage.acedetails.entity.Employee;
import com.garage.acedetails.entity.Goods;
import com.garage.acedetails.entity.GoodsCar;
import com.garage.acedetails.entity.GoodsCarPK;
import com.garage.acedetails.entity.Image;
import com.garage.acedetails.entity.RepairedCar;
import com.garage.acedetails.entity.UsedService;
import com.garage.acedetails.repository.AccountRepository;
import com.garage.acedetails.repository.BillRepository;
import com.garage.acedetails.repository.CarCareRepository;
import com.garage.acedetails.repository.CategoryRepository;
import com.garage.acedetails.repository.CustomerRepository;
import com.garage.acedetails.repository.EmployeeRepository;
import com.garage.acedetails.repository.GoodsCarRepository;
import com.garage.acedetails.repository.GoodsRepository;
import com.garage.acedetails.repository.ImageRepository;
import com.garage.acedetails.repository.RepairedCarRepository;
import com.garage.acedetails.repository.UsedServiceRepository;
import com.garage.acedetails.service.CartService;
import com.garage.acedetails.util.annotation.ExcelColumnIndex;

@Component
public class ExcelUtil {
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private GoodsRepository goodsRepository;
  @Autowired
  private ImageRepository imageRepository;
  @Autowired
  private RepairedCarRepository repairedCarRepository;
  @Autowired
  private GoodsCarRepository goodsCarRepository;
  @Autowired
  private BillRepository billRepository;
  @Autowired
  private UsedServiceRepository usedServiceRepository;
  @Autowired
  private CarCareRepository carCareRepository;
  @Autowired
  private CartService cartService;

  public Workbook getWorkbook(InputStream inputStream) throws IOException {
    return new XSSFWorkbook(inputStream);
  }

  public <T> List<T> getListObjectFromExcelFile(Class<T> objectClass, String sheetName, MultipartFile listObjectFile)
      throws IOException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, ParseException {
    Workbook workbook = getWorkbook(listObjectFile.getInputStream());
    return GetDataFromSheetAndMapToList(objectClass, sheetName, workbook);
  }

  // Temporary use, waiting to be replaced by flyway
  public boolean insertDataToDatabase() throws IOException, NoSuchFieldException, InvocationTargetException,
      NoSuchMethodException, InstantiationException, IllegalAccessException, ParseException {
    InputStream inputStream = Files.newInputStream(new File(ApplicationConstants.DATA_FILE).toPath());
    Workbook workbook = getWorkbook(inputStream);
    int tempSize;
    // Load Data From Sheet
    // Load Account
    List<Account> listOfAccounts =
        GetDataFromSheetAndMapToList(Account.class, ApplicationConstants.ACCOUNT_SHEET, workbook);
    listOfAccounts.forEach(account -> account.setPassword(passwordEncoder.encode(account.getPassword())));
    // Load Employee
    List<Employee> listOfEmployees =
        GetDataFromSheetAndMapToList(Employee.class, ApplicationConstants.EMPLOYEE_SHEET, workbook);
    // Load Customer
    List<Customer> listOfCustomers =
        GetDataFromSheetAndMapToList(Customer.class, ApplicationConstants.CUSTOMER_SHEET, workbook);
    // Load Category
    List<Category> listOfCategories =
        GetDataFromSheetAndMapToList(Category.class, ApplicationConstants.CATEGORY_SHEET, workbook);
    // Load Goods
    List<Goods> listOfGoods = GetDataFromSheetAndMapToList(Goods.class, ApplicationConstants.GOODS_SHEET, workbook);
    // Load Image
    List<Image> listOfImages = GetDataFromSheetAndMapToList(Image.class, ApplicationConstants.IMAGE_SHEET, workbook);
    // Load Repaired Car
    List<RepairedCar> listOfRepairedCars =
        GetDataFromSheetAndMapToList(RepairedCar.class, ApplicationConstants.REPAIRED_CAR_SHEET, workbook);
    // Load Goods_Car
    List<GoodsCar> listOfGoodsCars =
        GetDataFromSheetAndMapToList(GoodsCar.class, ApplicationConstants.GOODS_CAR_SHEET, workbook);
    // Load Bill
    List<Bill> listOfBills = GetDataFromSheetAndMapToList(Bill.class, ApplicationConstants.BILL_SHEET, workbook);
    // Load Used Service
    List<UsedService> listOfUsedServices =
        GetDataFromSheetAndMapToList(UsedService.class, ApplicationConstants.USED_SERVICE_SHEET, workbook);
    // Load Car_care
    List<CarCare> listOfCarCares =
        GetDataFromSheetAndMapToList(CarCare.class, ApplicationConstants.CAR_CARE_SHEET, workbook);
    // Insert Data To DB
    // The insert order is important
    listOfAccounts = accountRepository.saveAll(listOfAccounts);
    listOfCategories = categoryRepository.saveAll(listOfCategories);
    listOfCarCares = carCareRepository.saveAll(listOfCarCares);
    // Add account to employee
    tempSize = Math.min(listOfAccounts.size(), listOfEmployees.size());
    for (int i = 0; i < tempSize; i++) {
      listOfEmployees.get(i).setAccount(listOfAccounts.get(i));
    }
    employeeRepository.saveAll(listOfEmployees);
    // Add account to customer
    tempSize = Math.min(listOfAccounts.size(), listOfCustomers.size());
    for (int i = 0; i < tempSize; i++) {
      listOfCustomers.get(i).setAccount(listOfAccounts.get(i));
    }
    listOfCustomers = customerRepository.saveAll(listOfCustomers);
    // Create Cart for accounts have ROLE_USER
    for (int i = 0; i < listOfCustomers.size(); i++) {
      if (listOfCustomers.get(i).getAccount().getRole() == UserRole.ROLE_USER) {
        cartService.createCart(listOfCustomers.get(i).getId());
      }
    }
    // Add category to goods
    tempSize = Math.min(listOfCategories.size(), listOfGoods.size());
    for (int i = 0; i < tempSize; i++) {
      listOfGoods.get(i).setCategory(listOfCategories.get(i));
    }
    listOfGoods = goodsRepository.saveAll(listOfGoods);
    // Add goods to image
    tempSize = Math.min(listOfGoods.size(), listOfImages.size());
    for (int i = 0; i < tempSize; i++) {
      listOfImages.get(i).setGoods(listOfGoods.get(i));
    }
    imageRepository.saveAll(listOfImages);
    // Add customer to repaired car
    tempSize = Math.min(listOfCustomers.size(), listOfRepairedCars.size());
    for (int i = 0; i < tempSize; i++) {
      listOfRepairedCars.get(i).setCustomer(listOfCustomers.get(i));
    }
    listOfRepairedCars = repairedCarRepository.saveAll(listOfRepairedCars);
    // Add repaired car to bill
    tempSize = Math.min(listOfBills.size(), listOfRepairedCars.size());
    for (int i = 0; i < tempSize; i++) {
      listOfBills.get(i).setRepairedCar(listOfRepairedCars.get(i));
    }
    billRepository.saveAll(listOfBills);
    // Add goods to goods car
    listOfGoodsCars.forEach(goodsCar -> goodsCar.setGoodsCarPK(new GoodsCarPK()));
    tempSize = Math.min(listOfGoods.size(), listOfGoodsCars.size());
    for (int i = 0; i < tempSize; i++) {
      listOfGoodsCars.get(i).getGoodsCarPK().setGoods(listOfGoods.get(i));
    }
    // Add repaired car to goods car
    tempSize = Math.min(listOfRepairedCars.size(), listOfGoodsCars.size());
    for (int i = 0; i < tempSize; i++) {
      listOfGoodsCars.get(i).getGoodsCarPK().setRepairedCar(listOfRepairedCars.get(i));
    }
    goodsCarRepository.saveAll(listOfGoodsCars);
    // Add repaired car to used_service
    tempSize = Math.min(listOfRepairedCars.size(), listOfUsedServices.size());
    for (int i = 0; i < tempSize; i++) {
      listOfUsedServices.get(i).setRepairedCar(listOfRepairedCars.get(i));
      listOfUsedServices.get(i).getId().setRepairedCarId(listOfRepairedCars.get(i).getId());
    }
    // Add car care to used_service
    tempSize = Math.min(listOfCarCares.size(), listOfUsedServices.size());
    for (int i = 0; i < tempSize; i++) {
      listOfUsedServices.get(i).setCarCare(listOfCarCares.get(i));
      listOfUsedServices.get(i).getId().setCarCareNumber(listOfCarCares.get(i).getNumber());
    }
    usedServiceRepository.saveAll(listOfUsedServices);
    return true;
  }

  // Read data from Excel Sheet (row by row)
  // For each row, create a new object by objectClass
  // If a field in object does not have annotation @ExcelColumnIndex, skip that field
  // Otherwise, get cell from row by using the value of @ExcelColumnIndex, then assign cell value to
  // that field
  // Add that new object to a List<Object> and return list<Object>
  public <T> List<T> GetDataFromSheetAndMapToList(Class<T> objectClass, String sheetName, Workbook workbook)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException,
      NoSuchFieldException, ParseException {
    Sheet sheet = workbook.getSheet(sheetName);
    List<T> listOfObjects = new ArrayList<>();
    T object;
    DataFormatter dataFormatter = new DataFormatter();
    DateTimeFormatter dateTimeFormatter =
        new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String value;
    Field[] objectFields = objectClass.getDeclaredFields();
    for (Row nextRow : sheet) {
      if (nextRow.getRowNum() == 0) {
        continue;
      }
      object = objectClass.getConstructor().newInstance();
      for (Field field : objectFields) {
        Field objectField = object.getClass().getDeclaredField(field.getName());
        if (objectField.getAnnotation(ExcelColumnIndex.class) == null) {
          continue;
        }
        objectField.setAccessible(true); // disable field access modifier
        Cell cell = nextRow.getCell(objectField.getAnnotation(ExcelColumnIndex.class).index());
        value = dataFormatter.formatCellValue(cell);
        Class<?> objectFieldType = objectField.getType();
        if (objectFieldType.equals(Long.class) || objectFieldType.equals(Long.TYPE)) {
          objectField.set(object, Long.valueOf(value));
        } else if (objectFieldType.equals(Double.class) || objectFieldType.equals(Double.TYPE)) {
          objectField.set(object, Double.valueOf(value));
        } else if (objectFieldType.equals(Integer.class) || objectFieldType.equals(Integer.TYPE)) {
          objectField.set(object, Integer.valueOf(value));
        } else if (objectFieldType.equals(String.class)) {
          objectField.set(object, value);
        } else if (objectFieldType.equals(LocalDateTime.class)) {
          objectField.set(object, LocalDateTime.parse(value, dateTimeFormatter));
        } else if (objectFieldType.equals(Date.class)) {
          // java.util.Date
          objectField.set(object, simpleDateFormat.parse(value));
        } else if (objectFieldType.equals(UserRole.class)) {
          objectField.set(object, UserRole.valueOf(value));
        }
      }
      listOfObjects.add(object);
    }
    return listOfObjects;
  }
}
