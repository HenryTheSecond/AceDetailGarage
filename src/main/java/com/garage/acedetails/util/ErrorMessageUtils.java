package com.garage.acedetails.util;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ErrorMessageUtils {
  public static String methodArgumentNotValidExceptionMessageUtils(MethodArgumentNotValidException exception) {
    String errors = "";
    String fieldName = "";
    String errorMessage = "";
    for (ObjectError error : exception.getBindingResult().getAllErrors()) {
      fieldName = ((FieldError) error).getField();
      errorMessage = error.getDefaultMessage();
      errors += fieldName + ": " + errorMessage + "\n";
    }
    return errors;
  }
}
