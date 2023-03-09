package com.garage.acedetails.service.impl;

import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.goods.GoodsAddDto;
import com.garage.acedetails.dto.goods.GoodsSimpleInfoDto;
import com.garage.acedetails.entity.Goods;
import com.garage.acedetails.entity.Image;
import com.garage.acedetails.mapper.GoodsMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.CategoryRepository;
import com.garage.acedetails.repository.GoodsRepository;
import com.garage.acedetails.service.GoodsService;
import com.garage.acedetails.service.ImageService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GoodsServiceImpl implements GoodsService {

  @Autowired
  private GoodsRepository goodsRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private GoodsMapper goodsMapper;
  @Autowired
  private ImageService imageService;

  public List<Goods> findAll() {
    return goodsRepository.findAll();
  }

  public DataResponse findById(Long id) {
    Goods goods =
        goodsRepository.findById(id).orElseThrow(() -> new RuntimeException(ApplicationConstants.GOODS_NOT_FOUND));
    return new DataResponse(goodsMapper.entityToGoodsSimpleInfo(goods));
  }

  public DataResponse insertGoods(GoodsAddDto goodsAddDto, List<MultipartFile> files) {
    Goods goods = goodsMapper.goodsAddDtoToEntity(goodsAddDto);
    goodsRepository.save(goods);
    goods.setListOfImage(new ArrayList<>());
    files.forEach(file -> {
      String fileName = imageService.saveToDirectory(file);
      Image image = imageService.saveToDatabase(new Image(null, fileName, goods));
      goods.getListOfImage().add(image);
    });
    return new DataResponse(goodsMapper.entityToGoodsSimpleInfo(goods));
  }

  public DataResponse updateGoods(Long id, GoodsAddDto goodsAddDto) {
    // Optional<Goods> optionalGoods = goodsRepository.findById(id);
    // if (GoodsValidator.isValid(goods)) {
    // if (optionalGoods.isPresent()) {
    // Goods goodsFromDB = optionalGoods.get();
    // goodsFromDB.setName(goods.getName());
    // goodsFromDB.setPrice(goods.getPrice());
    // goodsFromDB.setTotalQuantity(goods.getTotalQuantity());
    // // goodsFromDB.setCategory(goods.getCategory());
    // goodsFromDB.setSetOfGoodsCar(goods.getSetOfGoodsCar());
    // goodsRepository.save(goodsFromDB);
    // return true;
    // } else {
    // System.out.println("Goods not found");
    // }
    // } else {
    // System.out.println("Goods not valid");
    // }
    // return false;

    Goods goodsDb =
        goodsRepository.findById(id).orElseThrow(() -> new RuntimeException(ApplicationConstants.GOODS_NOT_FOUND));
    goodsDb.setName(goodsAddDto.getName() != null ? goodsAddDto.getName() : goodsDb.getName());
    goodsDb.setPrice(goodsAddDto.getPrice());
    goodsDb.setTotalQuantity(goodsAddDto.getTotalQuantity());
    goodsDb
        .setDescription(goodsAddDto.getDescription() != null ? goodsAddDto.getDescription() : goodsDb.getDescription());
    categoryRepository.findById(goodsAddDto.getIdCategory()).ifPresent((category) -> goodsDb.setCategory(category));
    Goods result = goodsRepository.saveAndFlush(goodsDb);
    return new DataResponse(goodsMapper.entityToGoodsSimpleInfo(result));
  }

  public DataResponse deleteGoods(long id) {
    goodsRepository.deleteById(id);
    return new DataResponse(true);
  }

  @Override
  public DataResponse searchGoods(String keyword, String strMaxPrice, String strMinPrice, String strIdCategory,
      String strPage) {
    // Validate filter param
    Integer page = 1;
    Double maxPrice;
    Double minPrice;
    Long idCategory;
    try {
      maxPrice = strMaxPrice != null ? Double.parseDouble(strMaxPrice) : null;
      minPrice = strMinPrice != null ? Double.parseDouble(strMinPrice) : null;
      idCategory = strIdCategory != null ? Long.parseLong(strIdCategory) : null;
      page = strPage != null ? Integer.parseInt(strPage) : 1;
      page = page <= 0 ? 1 : page;
    } catch (NumberFormatException e) {
      throw new RuntimeException(ApplicationConstants.BAD_REQUEST);
    }

    Page<Goods> pageGoods =
        goodsRepository.searchAndFilterGoods(keyword, maxPrice, minPrice, idCategory, PageRequest.of(--page, 2));
    List<Goods> listGoods = pageGoods.toList();
    List<GoodsSimpleInfoDto> listGoodsSimpleInfoDto = new ArrayList<>(listGoods.size());
    listGoods.forEach(goods -> listGoodsSimpleInfoDto.add(goodsMapper.entityToGoodsSimpleInfo(goods)));
    Map<String, Object> result = new HashMap<>();
    result.put("result", listGoodsSimpleInfoDto);
    result.put("maxPage", pageGoods.getTotalPages());
    result.put("currentPage", ++page);
    return new DataResponse(result);
  }
}
