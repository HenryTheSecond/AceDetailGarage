package com.garage.acedetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.garage.acedetails.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
