package com.garage.acedetails.model;

import com.garage.acedetails.constants.ApplicationConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {
  private String error;
  private String message;
  private Object data;
  private int status = 0;

  public DataResponse(Object data) {
    this.message = ApplicationConstants.SUCCESSFUL;
    this.error = ApplicationConstants.SUCCESSFUL;
    this.data = data;
    this.status = ApplicationConstants.SUCCESSFUL_CODE;
  }

  public DataResponse(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public DataResponse(String error, String message, int status) {
    this.error = error;
    this.message = message;
    this.status = status;
  }



   public static final DataResponse SUCCESSFUL = new DataResponse(ApplicationConstants.SUCCESSFUL,
   "SUCCESSFUL");
  // public static final DataResponse SESSION_EXPIRED = new
  // DataResponse(ApplicationConstants.SESSION_EXPIRED, "SESSION_EXPIRED");
   public static final DataResponse NOT_FOUND = new DataResponse(ApplicationConstants.NOT_FOUND,
   "NOT_FOUND");
  public static final DataResponse BAD_REQUEST = new DataResponse(ApplicationConstants.BAD_REQUEST,
      ApplicationConstants.BAD_REQUEST_MESSAGE, null, ApplicationConstants.BAD_REQUEST_CODE);
  // public static final DataResponse FORBIDDEN = new DataResponse(ApplicationConstants.FORBIDDEN,
  // "FORBIDDEN");
  // public static final DataResponse PERMISSION_DENY = new
  // DataResponse(ApplicationConstants.PERMISSION_DENY, "PERMISSION_DENY");
   public static final DataResponse FAILED = new DataResponse(ApplicationConstants.FAILED,
   "FAILED");
  // public static final DataResponse AUTH_FAILED = new DataResponse(ApplicationConstants.AUTH_FAILED,
  // "AUTH_FAILED");
  // public static final DataResponse IS_BLOCKED = new DataResponse(ApplicationConstants.IS_BLOCKED,
  // "IS_BLOCKED");
}
