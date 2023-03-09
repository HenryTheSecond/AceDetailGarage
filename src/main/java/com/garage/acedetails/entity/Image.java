package com.garage.acedetails.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import com.garage.acedetails.util.annotation.ExcelColumnIndex;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "BIGINT(19)")
  private Long id;

  @Column(name = "link", columnDefinition = "NVARCHAR(1000)", unique = true, nullable = false)
  @ExcelColumnIndex(index = 1)
  private String link;

  @ManyToOne
  @JoinColumn(name = "goods_number")
  @JsonIgnore
  private Goods goods;
}
