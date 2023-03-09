package com.garage.acedetails.service;

import com.garage.acedetails.dto.goods.GoodsAddDto;
import com.garage.acedetails.entity.Goods;
import com.garage.acedetails.model.DataResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface GoodsService {

  public List<Goods> findAll();

  public DataResponse findById(Long id);

  public DataResponse insertGoods(GoodsAddDto goodsAddDto, List<MultipartFile> files);

  public DataResponse updateGoods(Long id, GoodsAddDto goods);

  public DataResponse deleteGoods(long id);
  
  public DataResponse searchGoods(String keyword, String strMaxPrice, String strMinPrice, String strIdCategory, String strPage);
}
