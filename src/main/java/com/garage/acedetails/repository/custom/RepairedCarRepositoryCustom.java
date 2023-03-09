package com.garage.acedetails.repository.custom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import com.garage.acedetails.entity.RepairedCar;

public interface RepairedCarRepositoryCustom {
  public Map<String, Object> searchRepairedCar(String keyword, String keywordType, List<Long> listIdCarCares,
      String dateType, LocalDateTime from, LocalDateTime to, int page, String orderBy, String order);
}
