package com.garage.acedetails.mapper;

import org.mapstruct.Mapper;
import com.garage.acedetails.dto.image.ImageDto;
import com.garage.acedetails.entity.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {
  ImageDto entityToImageDto(Image image);
  Image imageDtoToEntity(ImageDto imageDto);
}
