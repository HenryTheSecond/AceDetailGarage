package com.garage.acedetails.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.entity.CarCare;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.CarCareRepository;
import com.garage.acedetails.service.CarCareService;

@Service
public class CarCareServiceImpl implements CarCareService {

  @Autowired
  private CarCareRepository carCareRepository;

  @Override
  public DataResponse addCarCare(CarCare carCare) {
    String serviceName = carCare.getServiceName();
    if (carCareRepository.findByServiceName(serviceName) != null) {
      throw new RuntimeException(ApplicationConstants.CAR_SERVICE_NAME_IS_DUPLICATED);
    }
    return new DataResponse(carCareRepository.save(carCare));
  }

  @Override
  public DataResponse findAllCarCare() {
    return new DataResponse(carCareRepository.findAll());
  }

  @Override
  public DataResponse findCarCareById(Long id) {
    CarCare carService =
        carCareRepository.findById(id).orElseThrow(() -> new RuntimeException(ApplicationConstants.SERVICE_NOT_FOUND));
    return new DataResponse(carService);
  }

  @Override
  public DataResponse deleteCarCare(Long id) {
    carCareRepository.deleteById(id);
    return new DataResponse(true);
  }

  @Override
  public DataResponse updateCarCare(Long id, CarCare carCare) {
//    Optional<CarCare> optionalCarServiceDB = carCareRepository.findById(id);
//    if (!optionalCarServiceDB.isPresent()) {
//      throw new RuntimeException(ApplicationConstants.CAR_NOT_FOUND);
//    }
//    CarCare carCareDB = optionalCarServiceDB.get();
//    if (carCare.getServiceName() != null && !carCare.getServiceName().trim().equals("")) {
//      carCareDB.setServiceName(carCare.getServiceName());
//    }
//    if (carCare.getDescription() != null && !carCare.getDescription().trim().equals("")) {
//      carCareDB.setDescription(carCare.getDescription());
//    }
//    if (carCare.getPrice() <= 0) {
//
//    } else {
//      carCareDB.setPrice(carCare.getPrice());
//    }
    
    CarCare carCareDatabase = carCareRepository.findById(id).orElseThrow(()->new RuntimeException(ApplicationConstants.CAR_CARE_NOT_FOUND));
    if (carCare.getServiceName() != null && !carCare.getServiceName().trim().equals("")) {
      carCareDatabase.setServiceName(carCare.getServiceName());
    }
    if (carCare.getDescription() != null && !carCare.getDescription().trim().equals("")) {
      carCareDatabase.setDescription(carCare.getDescription());
    }
    if(carCare.getPrice()>=0) {
      carCareDatabase.setPrice(carCare.getPrice());
    }
    return new DataResponse(carCareRepository.save(carCareDatabase));
  }
}
