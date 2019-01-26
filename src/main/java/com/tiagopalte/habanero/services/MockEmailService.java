package com.tiagopalte.habanero.services;

import javax.mail.internet.MimeMessage;

import com.tiagopalte.habanero.domain.Client;
import com.tiagopalte.habanero.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Order order) {
		LOG.info("Simulando envio de email em formato HTML...");
		LOG.info("Pedido confirmado! Código: " + order.getId());
		LOG.info("Email enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de email HTML...");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
	}

}
