package com.garage.acedetails.constants;

public class ApplicationConstants {
  public static final String EMAIL_PATTERN =
      "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
  public static final String SUCCESSFUL = "SUCCESSFUL";
  public static final int SUCCESSFUL_CODE = 200;
  public static final String FAILED = "FAILED";
  public static final int FAILED_CODE = 400;
  public static final String UNAUTHORIZED = "UNAUTHORIZED";
  public static final int UNAUTHORIZED_CODE = 401;
  public static final String FORBIDDEN = "FORBIDDEN";
  public static final int FORBIDDEN_CODE = 403;
  public static final String BAD_REQUEST = "BAD_REQUEST";
  public static final int BAD_REQUEST_CODE = 400;
  public static final String BAD_REQUEST_MESSAGE = "Bad request";
  public static final String NOT_FOUND = "NOT_FOUND";
  public static final int NOT_FOUND_CODE = 400;
  public static final String IS_DUPLICATED = "IS_DUPLICATED";
  public static final int DUPLICATED_CODE = 400;
  public static final String WRONG_FORMAT = "WRONG FORMAT";
  public static final String WRONG_FORMAT_MESSAGE = "File must be in .xlsx format";
  public static final String SERVICE_NOT_FOUND = "SERVICE_NOT_FOUND";
  public static final String CAR_NOT_FOUND = "CAR_NOT_FOUND";
  public static final String CREATED = "CREATED";
  public static final int CREATED_CODE = 201;
  public static final String CAR_SERVICE_NAME_IS_DUPLICATED = "CAR_SERVICE_NAME_IS_DUPLICATED";
  public static final String CAR_CARE_HAS_BEEN_USED = "CAR_CARE_HAS_BEEN_USED";
  public static final String CAR_CARE_NOT_FOUND = "CAR_CARE_NOT_FOUND";
  public static final String PARAMS_INPUT_INVALID = "PARAMS_INPUT_INVALID";
  public static final String ACCOUNT_ROLE_INVALID = "Account role is invalid";
  public static final String ACCOUNT_ID_INVALID = "Account id is invalid";
  public static final String BILL_NOT_FOUND = "BILL_NOT_FOUND";
  public static final String BILL_ALREADY_EXISTS = "BILL_ALREADY_EXISTS";
  public static final int PAGE_SIZE_OF_GET_ALL_CUSTOMER = 3;
  public static final int GOODS_BILL_PAGE_SIZE = 5;
  public static final String GOODS_BILL_NOT_FOUND = "Goods bill not found";
  public static final String GOODS_NOT_FOUND = "GOODS_NOT_FOUND";
  public static final String CUSTOMER_NOT_FOUND = "CUSTOMER NOT FOUND";
  public static final String PAYMENT_METHOD_INVALID = "PAYMENT METHOD INVALID";
  public static final String GOODS_CAR_NOT_FOUND = "GOODS CAR NOT FOUND";
  public static final String PHONE_EXISTED = "PHONE EXISTED";
  public static final String EMPLOYEE_NOT_EXIST = "EMPLOYEE NOT EXIST";
  public static final String CATEGORY_NOT_FOUND = "CATEGORY_NOT_FOUND";
  public static final String DATA_FILE = "src\\main\\resources\\data\\data.xlsx";
  public static final int EMPLOYEE_PAGE_SIZE = 5;
  public static final int GOODS_CAR_PAGE_SIZE = 5;
  public static final String GOODS_CAR_EXISTED = "GOODS CAR EXISTED";
  public static final String IMAGE_DIRECTORY = "src\\main\\resources\\images";
  public static final String GOODSBILL_DIRECTORY = "src\\main\\resources\\data\\goodsbill.xlsx";
  public static final String ACCOUNT_SHEET = "Account";
  public static final String EMPLOYEE_SHEET = "Employee";
  public static final String CUSTOMER_SHEET = "Customer";
  public static final String CATEGORY_SHEET = "Category";
  public static final String GOODS_SHEET = "Goods";
  public static final String IMAGE_SHEET = "Image";
  public static final String REPAIRED_CAR_SHEET = "RepairedCar";
  public static final String GOODS_CAR_SHEET = "GoodsCar";
  public static final String BILL_SHEET = "Bill";
  public static final String USED_SERVICE_SHEET = "UsedService";
  public static final String CAR_CARE_SHEET = "CarCare";
  public static final String GOODS_BILL_DETAILS_SHEET = "BuyGoods";
  public static final int REPAIRED_CAR_PAGE_SIZE = 2;
  // For registration email verification
  public static final int CONFIRM_TOKEN_DURATION = 5; // minute
  public static final String VERIFICATION_REGISTER_EMAIL_SUBJECT = "Verification for registration";
  public static final String USERNAME_IS_DUPLICATED = "USERNAME_IS_DUPLICATED";
  public static final String EMAIL_IS_DUPLICATED = "EMAIL_IS_DUPLICATED";
  public static final String TOKEN_IS_NOT_FOUND = "TOKEN_IS_NOT_FOUND";
  public static final String EMAIL_HAS_BEEN_CONFIRMED = "EMAIL_HAS_BEEN_CONFIRMED";
  public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
  public static final String ORDER_ASCENDING = "ASC";
  public static final String ORDER_DESCENDING = "DESC";
  public static final String UNEXPECTED_ERROR = "Something wrong happened please try again later!";
  public static final String ACCOUNT_INACTIVE = "Login failed: your account has not been activated";
  // public static final String USERNAME_OR_PASSWORD_INVALID = "Login failed: Username or password is
  // invalid";
  public static final String USERNAME_OR_PASSWORD_INCORRECT = "Login failed: username or password is incorrect";
  public static final String USERNAME_OR_PASSWORD_MISSING = "Login failed: username or password is missing";
  public static final String USER_UNAUTHORIZED = "You don't have permission to access this resource";
  public static final String JWT_TOKEN_MISSING = "Jwt token is missing";
  
  public static final int GOODS_BILL_DETAIL_PAGE_SIZE = 10;
  public static final String GOODS_BILL_EXISTED = "GOODS_BILL_EXISTED";
  
}
