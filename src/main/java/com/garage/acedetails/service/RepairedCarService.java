package com.garage.acedetails.service;


import com.garage.acedetails.dto.repairedcar.RepairedCarAddDto;
import com.garage.acedetails.entity.RepairedCar;
import com.garage.acedetails.model.DataResponse;
import java.util.List;
import java.util.Map;


public interface RepairedCarService {

  public DataResponse addRepairedCar(RepairedCarAddDto repairedCarAddDto);
  public List<RepairedCar> findAllRepairedCar();
  public DataResponse findRepairedCarById(Long id);
  public DataResponse deleteRepairedCarById(Long id);
  public DataResponse updateRepairedCar(Long id, RepairedCar repairedCar);
  public DataResponse addCarCareToCar(Map<String, Object> idRepairedCarAndIdService);
  public DataResponse findRepairedCarWithPage(int page);
  public DataResponse searchRepairedCar(String keyword, String keywordType, String strIdCarCares, String dateType,
      String strFrom, String strTo, String strPage, String orderBy, String order);
}
