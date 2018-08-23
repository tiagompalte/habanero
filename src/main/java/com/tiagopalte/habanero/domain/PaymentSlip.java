package com.tiagopalte.habanero.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tiagopalte.habanero.domain.enums.PaymentState;

@Entity
@JsonTypeName("paymentSlip")
public class PaymentSlip extends Payment {
	
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date dueDate;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date payDate;
	
	public PaymentSlip() {
		super();
	}
	
	public PaymentSlip(Integer id, PaymentState state, Order order) {
		super(id, state, order);
	}
	
	public PaymentSlip(Integer id, PaymentState state, Order order, Date dueDate, Date payDate) {
		super(id, state, order);
		this.dueDate = dueDate;
		this.payDate = payDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@Override
	public int hashCode() {	
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
		
}
