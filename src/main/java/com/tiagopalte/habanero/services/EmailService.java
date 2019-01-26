package com.tiagopalte.habanero.services;

import javax.mail.internet.MimeMessage;

import com.tiagopalte.habanero.domain.Client;
import org.springframework.mail.SimpleMailMessage;

import com.tiagopalte.habanero.domain.Order;

public interface EmailService {

	void sendOrderConfirmationEmail(Order order);

	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationHtmlEmail(Order order);

	void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Client client, String newPass);
}
