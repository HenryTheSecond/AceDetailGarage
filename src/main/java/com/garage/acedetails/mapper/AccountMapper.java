package com.garage.acedetails.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.garage.acedetails.dto.AccountDto;
import com.garage.acedetails.dto.account.AccountSimpleInfoDto;
import com.garage.acedetails.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
  
  AccountDto entityToAccountDto(Account account);
  Account accountDtoToEntity(AccountDto accountDto);
  
  AccountSimpleInfoDto entityToAccountSimpleInfoDto(Account account);
  Account accountSimpleInfoDtoToEntity(AccountSimpleInfoDto accountSimpleInfoDto);
}
