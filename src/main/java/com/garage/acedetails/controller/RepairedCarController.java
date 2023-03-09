package com.garage.acedetails.controller;


import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.garage.acedetails.dto.repairedcar.RepairedCarAddDto;
import com.garage.acedetails.entity.RepairedCar;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.RepairedCarService;
import com.garage.acedetails.util.ValidId;

@RequestMapping("")
@RestController
@Validated
public class RepairedCarController {

  @Autowired
  private RepairedCarService repairedCarService;

  @PostMapping("/add-customer-and-car")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse addCustomerAndCar(@Valid @RequestBody RepairedCarAddDto repairedCarAddDto) {
    return repairedCarService.addRepairedCar(repairedCarAddDto);
  }

  @GetMapping("/car/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse getRepairedCarById(@PathVariable("id") @ValidId String strId) {
    Long id = Long.parseLong(strId);
    return repairedCarService.findRepairedCarById(id);
  }

  @GetMapping("/car")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse getRepairedCars(@RequestParam(defaultValue = "1", name = "page") String strPage) {
    int page = 0;
    try {
      page = Integer.parseInt(strPage);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      throw new NumberFormatException("Page is not valid");
    }
    return repairedCarService.findRepairedCarWithPage(page);
  }

  @DeleteMapping("/delete-car/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse deleteRepairedCarById(@PathVariable("id") @ValidId String strId) {
    Long id = Long.parseLong(strId);
    return repairedCarService.deleteRepairedCarById(id);
  }

  @PutMapping("/update-car/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse updateRepairedCarById(@PathVariable("id") @ValidId String strId,
      @RequestBody RepairedCar repairedCar) {
    Long id = Long.parseLong(strId);
    return repairedCarService.updateRepairedCar(id, repairedCar);
  }

  @PostMapping("/add-car-care-to-car")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse addCarCareToCar(@RequestBody Map<String, Object> body) {
    return repairedCarService.addCarCareToCar(body);
  }
  
  
  
  @GetMapping("/search-repaired-car")
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse findRepairedCarByService(@RequestParam(name = "keyword", defaultValue = "") String keyword,
      @RequestParam(name = "keywordType", defaultValue = "carNumber") String keywordType,
      @RequestParam(name = "idCarCares", required = false) String strIdCarCares,
      @RequestParam(name = "dateType", required = false) String dateType,
      @RequestParam(name = "from", required = false) String strFrom,
      @RequestParam(name = "to", required = false) String strTo,
      @RequestParam(name = "page", required = false) String strPage,
      @RequestParam(name = "orderBy", required = false, defaultValue = "startDate") String orderBy,
      @RequestParam(name = "order", required = false, defaultValue = "desc") String order) {

    return repairedCarService.searchRepairedCar(keyword, keywordType, strIdCarCares, dateType, strFrom, strTo, strPage,
        orderBy, order);
  }
}
