package com.garage.acedetails.exception;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.util.ErrorMessageUtils;

@RestControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler {
  @ExceptionHandler(RuntimeException.class)
  public DataResponse generalException(RuntimeException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  // Use in case: type of input is wrong type
  @ExceptionHandler(NumberFormatException.class)
  public DataResponse badRequestNumberFormatException(NumberFormatException exception, WebRequest request) {
    return new DataResponse(exception.getMessage(), exception.getMessage(), null, HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public DataResponse failValidRequestBody(MethodArgumentNotValidException exception, WebRequest request) {
    String errors = ErrorMessageUtils.methodArgumentNotValidExceptionMessageUtils(exception);
    return new DataResponse(errors, errors, null, HttpStatus.BAD_REQUEST.value());
  }

  // Use in case: has error duplication when insert
  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public DataResponse sqlConstraintViolationException(SQLIntegrityConstraintViolationException exception,
      WebRequest request) {
    return new DataResponse(exception.getMessage(), exception.getMessage(), null, HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public DataResponse idInvalidException(ConstraintViolationException exception, WebRequest request) {
    return new DataResponse(exception.getMessage(), exception.getMessage(), null, HttpStatus.BAD_REQUEST.value());
  }

  // Exception when delete nonexisted object
  @ExceptionHandler(EmptyResultDataAccessException.class)
  public DataResponse emptyResultDataAccessException(EmptyResultDataAccessException exception, WebRequest request) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public DataResponse illegalArgumentExceptionDataResponse(IllegalArgumentException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(IOException.class)
  public DataResponse IOExceptionDataResponse(IOException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(NoSuchFieldException.class)
  public DataResponse NoSuchFieldExceptionDataResponse(NoSuchFieldException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(InvocationTargetException.class)
  public DataResponse InvocationTargetExceptionDataResponse(InvocationTargetException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(NoSuchMethodException.class)
  public DataResponse NoSuchMethodExceptionDataResponse(NoSuchMethodException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(InstantiationException.class)
  public DataResponse InstantiationExceptionDataResponse(InstantiationException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(IllegalAccessException.class)
  public DataResponse IllegalAccessExceptionDataResponse(IllegalAccessException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(ParseException.class)
  public DataResponse ParseExceptionDataResponse(ParseException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public DataResponse httpMessageNotReadableExceptionDataResponse(HttpMessageNotReadableException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  public DataResponse InvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }

  // Used when required request part "xx" is not present
  @ExceptionHandler(MissingServletRequestPartException.class)
  public DataResponse MissingServletRequestPartException(MissingServletRequestPartException exception) {
    return new DataResponse(ApplicationConstants.BAD_REQUEST, exception.getMessage(), null,
        HttpStatus.BAD_REQUEST.value());
  }
}
