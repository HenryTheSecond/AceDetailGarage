package com.garage.acedetails.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.garage.acedetails.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findAccountByUsername(String username);
  Optional<Account> findByEmail(String email);
}
