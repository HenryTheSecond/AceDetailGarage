package com.garage.acedetails.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.garage.acedetails.entity.ConfirmToken;

@Repository
public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, Long> {
  Optional<ConfirmToken> findByToken(String token);
}
