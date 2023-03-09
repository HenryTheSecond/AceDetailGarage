package com.garage.acedetails.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.garage.acedetails.dto.goods.GoodsAddDto;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.service.GoodsService;
import com.garage.acedetails.util.ValidId;

@RestController
@RequestMapping("")
@Validated
public class GoodsController {
  @Autowired
  private GoodsService goodsService;

  @PostMapping("/add-goods")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
  public DataResponse addGoods(@RequestPart("goods") @Valid GoodsAddDto goodsAddDto,
      @RequestParam("files") List<MultipartFile> files) {
    return goodsService.insertGoods(goodsAddDto, files);
  }

  @GetMapping("/goods/{id}")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse getGoodsById(@PathVariable("id") @ValidId String strId) {
    Long id = Long.parseLong(strId);
    return goodsService.findById(id);
  }

  @PutMapping("/goods/{goodsId}")
  public DataResponse updateGoods(@RequestBody GoodsAddDto goodsAddDto, @PathVariable long goodsId) {
    return goodsService.updateGoods(goodsId, goodsAddDto);
  }

  @DeleteMapping("/goods/{goodsId}")
  public DataResponse deleteGoods(@PathVariable long goodsId) {
    return goodsService.deleteGoods(goodsId);
  }

  @GetMapping("/goods")
  @ResponseStatus(HttpStatus.OK)
  public DataResponse searchGoods(@RequestParam(name = "keyword", required = false) String keyword,
      @RequestParam(name = "maxPrice", required = false) String strMaxPrice,
      @RequestParam(name = "minPrice", required = false) String strMinPrice,
      @RequestParam(name = "idCategory", required = false) String strIdCategory,
      @RequestParam(name = "page", required = false) String strPage) {
    return goodsService.searchGoods(keyword, strMaxPrice, strMinPrice, strIdCategory, strPage);
  }
}
