package com.tiagopalte.habanero.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.tiagopalte.habanero.domain.PaymentSlip;

@Service
public class PaymentSlipService {

	public void fillPaymentSlip(PaymentSlip slip, Date orderInstant) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(orderInstant);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		slip.setDueDate(calendar.getTime());
	}
	
}
