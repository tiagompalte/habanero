package com.tiagopalte.habanero.domain;

import javax.persistence.Entity;

import com.tiagopalte.habanero.domain.enums.PaymentState;

@Entity
public class CardPayment extends Payment {
	
	private static final long serialVersionUID = 1L;
	
	private Integer installments;

	public CardPayment() {
		super();
	}

	public CardPayment(Integer id, PaymentState state, Order order) {
		super(id, state, order);
	}
	
	public CardPayment(Integer id, PaymentState state, Order order, Integer installments) {
		super(id, state, order);
		this.installments = installments;
	}

	public Integer getInstallments() {
		return installments;
	}

	public void setInstallments(Integer installments) {
		this.installments = installments;
	}
	
}
