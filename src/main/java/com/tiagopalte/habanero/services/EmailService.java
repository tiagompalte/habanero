package com.tiagopalte.habanero.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.tiagopalte.habanero.domain.Order;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Order order);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Order order);
	
	void sendHtmlEmail(MimeMessage msg);

}
