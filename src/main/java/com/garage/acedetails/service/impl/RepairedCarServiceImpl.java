package com.garage.acedetails.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.repairedcar.RepairedCarAddDto;
import com.garage.acedetails.dto.repairedcar.RepairedCarAndCustomerDto;
import com.garage.acedetails.dto.repairedcar.RepairedCarDto;
import com.garage.acedetails.entity.CarCare;
import com.garage.acedetails.entity.Customer;
import com.garage.acedetails.entity.RepairedCar;
import com.garage.acedetails.entity.UsedService;
import com.garage.acedetails.entity.UsedServicePK;
import com.garage.acedetails.mapper.CustomerMapper;
import com.garage.acedetails.mapper.RepairedCarMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.CarCareRepository;
import com.garage.acedetails.repository.CustomerRepository;
import com.garage.acedetails.repository.RepairedCarRepository;
import com.garage.acedetails.repository.UsedServiceRepository;
import com.garage.acedetails.service.RepairedCarService;

@Service
public class RepairedCarServiceImpl implements RepairedCarService {
  @Autowired
  private RepairedCarRepository repairedCarRepository;
  @Autowired
  private CarCareRepository carCareRepository;
  @Autowired
  private UsedServiceRepository usedServiceRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private RepairedCarMapper repairedCarMapper;
  @Autowired
  private CustomerMapper customerMapper;

  @Override
  public DataResponse addRepairedCar(RepairedCarAddDto repairedCarAddDto) {
    Customer customer =
        customerRepository.save(customerMapper.customerRequestDtoToEntity(repairedCarAddDto.getCustomer()));
    RepairedCar repairedCar = repairedCarMapper.repairedCarAddDtoToEntity(repairedCarAddDto);
    repairedCar.setCustomer(customer);
    repairedCar.setStartDate(LocalDateTime.now());
    RepairedCar result = repairedCarRepository.save(repairedCar);
    Long idRepairedCar = result.getId();
    CarCare carCare = null;
    for (Long idCarCare : repairedCarAddDto.getListIdCarCare()) {
      carCare = carCareRepository.findById(idCarCare)
          .orElseThrow(() -> new RuntimeException(ApplicationConstants.SERVICE_NOT_FOUND));
      UsedService usedService = new UsedService(new UsedServicePK(idRepairedCar, idCarCare), carCare.getPrice());
      usedServiceRepository.save(usedService);
    }
    return new DataResponse(result);
  }

  @Override
  public List<RepairedCar> findAllRepairedCar() {
    return repairedCarRepository.findAll();
  }

  @Override
  public DataResponse findRepairedCarById(Long id) {
    RepairedCar repairedCar =
        repairedCarRepository.findById(id).orElseThrow(() -> new RuntimeException(ApplicationConstants.CAR_NOT_FOUND));
    RepairedCarAndCustomerDto repairedCarAndCustomerDto =
        repairedCarMapper.entityToRepairedCarAndCustomerDto(repairedCar);
    return new DataResponse(repairedCarAndCustomerDto);
  }

  @Override
  public DataResponse deleteRepairedCarById(Long id) {
    repairedCarRepository.deleteById(id);
    return new DataResponse(true);
  }

  @Override
  public DataResponse updateRepairedCar(Long id, RepairedCar repairedCar) {
    RepairedCar carDB =
        repairedCarRepository.findById(id).orElseThrow(() -> new RuntimeException(ApplicationConstants.CAR_NOT_FOUND));
    if (repairedCar.getDescription() != null && !repairedCar.getDescription().trim().equals("")) {
      carDB.setDescription(repairedCar.getDescription());
    }
    if (repairedCar.getStartDate() != null) {
      carDB.setStartDate(repairedCar.getStartDate());
    }
    if (repairedCar.getEndDate() != null) {
      if (repairedCar.getEndDate().compareTo(carDB.getStartDate()) < 0) {
      }
      carDB.setEndDate(repairedCar.getEndDate());
    }
    if (repairedCar.getCarNumber() != null && !repairedCar.getCarNumber().trim().equals("")) {
      carDB.setCarNumber(repairedCar.getCarNumber());
    }
    if (repairedCar.getCarStatus() != null && !repairedCar.getCarStatus().trim().equals("")) {
      carDB.setCarStatus(repairedCar.getCarStatus());
    }
    if (repairedCar.getCarType() != null && !repairedCar.getCarType().trim().equals("")) {
      carDB.setCarType(repairedCar.getCarType());
    }
    return new DataResponse(repairedCarRepository.save(carDB));
  }

  @Override
  public DataResponse addCarCareToCar(Map<String, Object> idRepairedCarAndIdService) {
    Long idRepairedCar = Long.parseLong(idRepairedCarAndIdService.get("idRepairedCar").toString());
    Long idCarCare = Long.parseLong(idRepairedCarAndIdService.get("idCarCare").toString());
    RepairedCar repairedCar = repairedCarRepository.findById(idRepairedCar)
        .orElseThrow(() -> new RuntimeException(ApplicationConstants.CAR_NOT_FOUND));
    CarCare carCare = carCareRepository.findById(idCarCare)
        .orElseThrow(() -> new RuntimeException(ApplicationConstants.SERVICE_NOT_FOUND));
    Optional<UsedService> service = usedServiceRepository.findById(new UsedServicePK(idRepairedCar, idCarCare));
    if (service.isPresent()) {
      throw new RuntimeException(ApplicationConstants.CAR_CARE_HAS_BEEN_USED);
    }
    UsedService usedService = new UsedService(new UsedServicePK(idRepairedCar, idCarCare), carCare.getPrice());
    usedServiceRepository.save(usedService);
    RepairedCar repairedCarDb = repairedCarRepository.findById(idRepairedCar).get();
    repairedCarDb.getSetOfUsedService().add(usedService);
    return new DataResponse(repairedCarMapper.entityToRepairedCarAndCarCareDto(repairedCarDb));
  }

  public DataResponse findRepairedCarWithPage(int page) {
    page--;
    page = page < 0 ? 0 : page;
    Page<RepairedCar> repairedCarPage = repairedCarRepository.findAll(PageRequest.of(page, 2));
    List<RepairedCarDto> listRepairedCarDto = repairedCarPage.toList().stream()
        .map(repairedCar -> repairedCarMapper.entityToRepairedCarDto(repairedCar)).collect(Collectors.toList());
    Map<String, Object> result = new HashMap<>();
    result.put("repairedCars", listRepairedCarDto);
    result.put("maxPage", repairedCarPage.getTotalPages());
    result.put("page", ++page);
    return new DataResponse(result);
  }

  @Override
  public DataResponse searchRepairedCar(String keyword, String keywordType, String strIdCarCares, String dateType,
      String strFrom, String strTo, String strPage, String orderBy, String order) {
    List<Long> listIdCarCares = null;
    LocalDateTime from = null;
    LocalDateTime to = null;
    int page = 1;
    try {
      if (strIdCarCares != null) {
        // Parse listIdCarCares from string to list, each id car care in listIdCarCares string format is
        // separated by '.', example: 1,2 or 2,3
        listIdCarCares =
            Arrays.asList(strIdCarCares.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());
      }
      // Check the date type we want to filter then parse, we have 2 options: filter by startDate or
      // endDate
      if (dateType != null && (dateType.equals("startDate") || dateType.equals("endDate"))) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (strFrom != null) {
          from = LocalDate.parse(strFrom, formatter).atStartOfDay();
        }
        if (strTo != null) {
          to = LocalDate.parse(strTo, formatter).atTime(LocalTime.MAX);
        }
      }
      page = strPage == null ? 1 : Integer.parseInt(strPage);
      page = page <= 0 ? 1 : page;
      // Validate orderBy, we can order by startDate or endDate, if user don't pass or pass wrong orderBy,
      // the default is orderBy startDate
      orderBy = (orderBy.equals("startDate") || orderBy.equals("endDate")) ? orderBy : "startDate";
      // Validate order, if user don't pass or pass wrong order, the default is descending
      order = (order.equals("asc") || order.equals("desc")) ? order : "desc";
    } catch (NumberFormatException | DateTimeParseException e) {
      e.printStackTrace();
      throw new RuntimeException(ApplicationConstants.PARAMS_INPUT_INVALID);
    }
    Map<String, Object> result = repairedCarRepository.searchRepairedCar(keyword, keywordType, listIdCarCares, dateType,
        from, to, page, orderBy, order);
    List<RepairedCarAndCustomerDto> listRepairedCarAndCustomerDto = ((List<RepairedCar>) result.get("repairedCars"))
        .stream().map(repairedCarMapper::entityToRepairedCarAndCustomerDto).collect(Collectors.toList());
    result.put("repairedCars", listRepairedCarAndCustomerDto);
    return new DataResponse(result);
  }
}
