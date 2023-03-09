package com.garage.acedetails.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.GoodBillDetailsDTO;
import com.garage.acedetails.entity.GoodBillDetails;
import com.garage.acedetails.entity.GoodBillDetailsPK;
import com.garage.acedetails.entity.Goods;
import com.garage.acedetails.entity.GoodsBill;
import com.garage.acedetails.mapper.GoodsBillDetailsMapper;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.repository.GoodsBillDetailsRepository;
import com.garage.acedetails.repository.GoodsBillRepository;
import com.garage.acedetails.repository.GoodsRepository;
import com.garage.acedetails.service.GoodsBillDetailsSevice;

@Service
public class GoodsBillDetailsSeviceImpl implements GoodsBillDetailsSevice {
  @Autowired
  private GoodsBillDetailsRepository goodsBillDetailsRepository;

  @Autowired
  private GoodsRepository goodsRepository;

  @Autowired
  private GoodsBillRepository goodsBillRepository;

  @Autowired
  private GoodsBillDetailsMapper goodsBillDetailsMapper;

  @Override
  public DataResponse findAll(int page) {
    page = page > 0 ? (page - 1) : 1;
    List<GoodBillDetailsDTO> lBillDetailsDTOs = goodsBillDetailsRepository
        .findAll(PageRequest.of(page, ApplicationConstants.GOODS_BILL_DETAIL_PAGE_SIZE)).getContent().stream()
        .map(goodsBillDetailsMapper::GoodBillDetailsToGoodsBillDetailsDTO).collect(Collectors.toList());
    return lBillDetailsDTOs.size() > 0 ? new DataResponse(lBillDetailsDTOs) : DataResponse.NOT_FOUND;
  }

  @Override
  public DataResponse findById(String StrGoodsNumber, String StrBillNumber) {
    Long goodNumber = Long.parseLong(StrBillNumber);
    Long billNumber = Long.parseLong(StrGoodsNumber);
    Goods goods = getGoodsFromDB(goodNumber);
    GoodsBill goodsBill = getGoodsBillFromDB(billNumber);
    return goodsBillDetailsRepository.findById(new GoodBillDetailsPK(goods, goodsBill)).map(DataResponse::new)
        .orElseGet(() -> DataResponse.NOT_FOUND);
  }

  @Override
  public DataResponse findByGoodsBill(String StrId, int page) {
    Long billNumber = Long.parseLong(StrId);
    page = page > 0 ? (page - 1) : 1;
    List<GoodBillDetails> lBillDetails = goodsBillDetailsRepository.findByGoodBillDetailsPK_Goodsbill(billNumber,
        PageRequest.of(page, ApplicationConstants.GOODS_BILL_DETAIL_PAGE_SIZE));
    return lBillDetails.size() > 0 ? new DataResponse(lBillDetails) : DataResponse.NOT_FOUND;
  }

  @Override
  public DataResponse insertGoodsBillDetails(GoodBillDetailsDTO billDetailsDTO, int page) {
    GoodBillDetails billDetails = goodsBillDetailsMapper.GoodsBillDetailsDTOToGoodBillDetails(billDetailsDTO);
    if (billDetails.getGoodBillDetailsPK().getGoods() == null) {
      throw new RuntimeException(ApplicationConstants.GOODS_NOT_FOUND);
    }
    if (billDetails.getGoodBillDetailsPK().getGoodsbill() == null) {
      throw new RuntimeException(ApplicationConstants.GOODS_BILL_NOT_FOUND);
    }
    erichGoodBillDetails(billDetails);
    if (checkIfGoodBillDetailsExistend(billDetails.getGoodBillDetailsPK())) {
      return new DataResponse(ApplicationConstants.BAD_REQUEST,

          ApplicationConstants.GOODS_BILL_EXISTED,

          ApplicationConstants.BAD_REQUEST_CODE);

    }
    billDetails.setTotalPrice(billDetails.getPrice() * billDetails.getQuantity());
    return new DataResponse(goodsBillDetailsRepository.save(billDetails));
  }

  @Override
  public DataResponse updateGoodsBillDetails(String StrGoodsNumber, String StrBillNumber,
      GoodBillDetailsDTO billDetailsDTO) {
    Long goodNumber = Long.parseLong(StrBillNumber);
    Long billNumber = Long.parseLong(StrGoodsNumber);
    Goods goods = getGoodsFromDB(goodNumber);
    GoodsBill goodsBill = getGoodsBillFromDB(billNumber);
    // Check if exist in Database GoodBillDetails
    GoodBillDetails billDetailCurent = goodsBillDetailsRepository.findById(new GoodBillDetailsPK(goods, goodsBill))
        .orElseThrow(() -> new RuntimeException(ApplicationConstants.NOT_FOUND));
    // Update new value
    billDetailCurent.setPrice(billDetailsDTO.getPrice() == 0 ? billDetailCurent.getPrice() : billDetailsDTO.getPrice());
    billDetailCurent
        .setQuantity(billDetailsDTO.getPrice() == 0 ? billDetailCurent.getQuantity() : billDetailsDTO.getQuantity());

    billDetailCurent.setTotalPrice(billDetailCurent.getPrice() * billDetailCurent.getQuantity());
    return new DataResponse(goodsBillDetailsRepository.saveAndFlush(billDetailCurent));
  }

  /*
   * @Override public DataResponse deleteGoodBillDetalis(String StrGoodsNumber, String StrBillNumber)
   * { Long goodNumber = Long.parseLong(StrBillNumber); Long billNumber =
   * Long.parseLong(StrGoodsNumber); Goods goods = getGoodsFromDB(goodNumber); GoodsBill goodsBill =
   * getGoodsBillFromDB(billNumber); try { billDetailsRepository.deleteById(new
   * GoodBillDetailsPK(goods, goodsBill)); return DataResponse.SUCCESSFUL; } catch
   * (EmptyResultDataAccessException e) { return DataResponse.BAD_REQUEST; } }
   */

  private GoodsBill getGoodsBillFromDB(Long billNumber) {
    return goodsBillRepository.findById(billNumber)
        .orElseThrow(() -> new RuntimeException(ApplicationConstants.NOT_FOUND));
  }

  private Goods getGoodsFromDB(Long goodNumber) {
    return goodsRepository.findById(goodNumber).orElseThrow(() -> new RuntimeException(ApplicationConstants.NOT_FOUND));
  }

  private boolean checkIfGoodBillDetailsExistend(GoodBillDetailsPK goodBillDetailsPK) {
    return goodsBillDetailsRepository.findById(goodBillDetailsPK).isPresent();
  }

  private void erichGoodBillDetails(GoodBillDetails goodBillDetails) {
    Goods goods = getGoodsFromDB(goodBillDetails.getGoodBillDetailsPK().getGoods().getNumber());
    GoodsBill bill = getGoodsBillFromDB(goodBillDetails.getGoodBillDetailsPK().getGoodsbill().getId());
    goodBillDetails.getGoodBillDetailsPK().setGoods(goods);
    goodBillDetails.getGoodBillDetailsPK().setGoodsbill(bill);

  }
}
