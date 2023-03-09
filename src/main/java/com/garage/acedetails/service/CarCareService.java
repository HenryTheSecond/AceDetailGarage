package com.garage.acedetails.service;

import com.garage.acedetails.entity.CarCare;
import com.garage.acedetails.model.DataResponse;

public interface CarCareService {

  public DataResponse addCarCare(CarCare service);

  public DataResponse findAllCarCare();

  public DataResponse findCarCareById(Long id);

  public DataResponse deleteCarCare(Long id);

  public DataResponse updateCarCare(Long id, CarCare service);
}
