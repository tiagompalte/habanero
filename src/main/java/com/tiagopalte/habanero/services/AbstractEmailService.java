package com.tiagopalte.habanero.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.tiagopalte.habanero.domain.Order;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendOrderConfirmationEmail(Order order) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromOrder(order);
		sendEmail(sm);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Order order) {
		try {
			MimeMessage mime = prepareMimeMessageFromOrder(order);
			sendHtmlEmail(mime);
		}
		catch(MessagingException e) {
			sendOrderConfirmationEmail(order);
		}		
	}

	protected MimeMessage prepareMimeMessageFromOrder(Order order) throws MessagingException {
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeHelper = new MimeMessageHelper(mime, true);
		mimeHelper.setTo(order.getClient().getEmail());
		mimeHelper.setFrom(sender);
		mimeHelper.setSubject("Pedido confirmado! Código: " + order.getId());
		mimeHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeHelper.setText(htmlFromTemplatePedido(order), true);
		return mime;
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order order) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(order.getClient().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + String.valueOf(order.getId()));
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(order.toString());
		return sm;
	}
	
	protected String htmlFromTemplatePedido(Order order) {
		Context context = new Context();
		context.setVariable("order", order);
		return templateEngine.process("email/orderConfirmation", context);
	}

}
