package com.garage.acedetails.mapper;

import org.mapstruct.Mapper;
import com.garage.acedetails.dto.GoodBillDetailsDTO;
import com.garage.acedetails.entity.GoodBillDetails;

@Mapper(componentModel = "spring")
public interface GoodsBillDetailsMapper {
  GoodBillDetailsDTO GoodBillDetailsToGoodBillDetailsDTO(GoodBillDetails goodBillDetails);

  GoodBillDetails GoodsBillDetailsDTOToGoodBillDetails(GoodBillDetailsDTO goodsBillDetailsDTO);
  
  GoodBillDetailsDTO GoodBillDetailsToGoodsBillDetailsDTO(GoodBillDetails goodBillDetails);
}
