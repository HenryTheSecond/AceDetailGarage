package com.garage.acedetails.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.goods.GoodsAddDto;
import com.garage.acedetails.dto.goods.GoodsSimpleInfoDto;
import com.garage.acedetails.dto.image.ImageDto;
import com.garage.acedetails.entity.Category;
import com.garage.acedetails.entity.Goods;
import com.garage.acedetails.entity.Image;
import com.garage.acedetails.mapper.CategoryMapper;
import com.garage.acedetails.mapper.GoodsMapper;
import com.garage.acedetails.mapper.ImageMapper;
import com.garage.acedetails.repository.CategoryRepository;

@Component
public class GoodsMapperImpl implements GoodsMapper {
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private CategoryMapper categoryMapper;
  @Autowired
  private ImageMapper imageMapper;
  
  @Autowired
  private CategoryRepository categoryRepository;
  
  @Override
  public Goods goodsAddDtoToEntity(GoodsAddDto goodsAddDto) {
    if(goodsAddDto == null)
      return null;
    
    Goods goods = modelMapper.map(goodsAddDto, Goods.class);
    Category category = categoryRepository.findById(goodsAddDto.getIdCategory())
        .orElseThrow(() -> new RuntimeException(ApplicationConstants.CATEGORY_NOT_FOUND));
    goods.setCategory(category);
    return goods;
  }

  @Override
  public GoodsSimpleInfoDto entityToGoodsSimpleInfo(Goods goods) {
    if(goods == null)
      return null;
    
    GoodsSimpleInfoDto goodsSimpleInfoDto = modelMapper.map(goods, GoodsSimpleInfoDto.class);
    goodsSimpleInfoDto.setCategory(categoryMapper.entityToCategoryDto(goods.getCategory()));
    List<ImageDto> listOfImage = new ArrayList<>();
    for(Image image: goods.getListOfImage()) {
      listOfImage.add(imageMapper.entityToImageDto(image));
    }
    goodsSimpleInfoDto.setListOfImage(listOfImage);
    return goodsSimpleInfoDto;
  }

}
