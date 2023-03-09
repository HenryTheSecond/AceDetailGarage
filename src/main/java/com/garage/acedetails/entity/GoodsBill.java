package com.garage.acedetails.entity;

import com.garage.acedetails.util.PaymentMethod;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "goods_bill")
public class GoodsBill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT(19)")
	private Long id;

	@Column(name = "payment_method", nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@Column(name = "total_money", columnDefinition = "DOUBLE")
	private Double total;

	@Column(name = "purchase_date", nullable = false)
	@NotNull
	private LocalDate purchaseDate;

	@Column(name = "payment_date")
	private LocalDate paymentDate;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@OneToMany(mappedBy = "goodBillDetailsPK.goodsbill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<GoodBillDetails> setOfGoodsBillDetail;
	
}
