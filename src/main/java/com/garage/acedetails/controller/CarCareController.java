package com.garage.acedetails.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.entity.CarCare;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.CarCareService;
import com.garage.acedetails.util.ValidId;

@RequestMapping("")
@RestController
@Validated
public class CarCareController {

  @Autowired
  private CarCareService carCareService;

  @PostMapping("/add-car-care")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse addCarCare(@Valid @RequestBody CarCare carService) {
    // TODO handle sql constraint exception, create custom @Unique or validate in service implement
    // TODO handle price is not number
    return carCareService.addCarCare(carService);
  }

  @GetMapping("/car-care/{id}")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse getCarCareById(@PathVariable("id") @ValidId String strId) {
    long id = Long.parseLong(strId);
    return carCareService.findCarCareById(id);
  }

  @DeleteMapping("/delete-car-care/{id}")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse deleteCarCareById(@PathVariable("id") @ValidId String strId) {
    Long id = Long.parseLong(strId);
    return carCareService.deleteCarCare(id);
  }

  @PutMapping("/update-car-care/{id}")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse updateCarCareById(@PathVariable("id") @ValidId String strId,
      @RequestBody @Valid CarCare carService) throws NumberFormatException {
    Long id = Long.parseLong(strId);
    return carCareService.updateCarCare(id, carService);
  }

}
