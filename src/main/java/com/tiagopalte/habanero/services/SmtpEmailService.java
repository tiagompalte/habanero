package com.tiagopalte.habanero.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.tiagopalte.habanero.domain.Client;
import com.tiagopalte.habanero.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

public class SmtpEmailService extends AbstractEmailService {
	
	@Autowired
	private MailSender mailSender;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		mailSender.send(msg);
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		javaMailSender.send(msg);
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

	private MimeMessage prepareMimeMessageFromOrder(Order order) throws MessagingException {
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeHelper = new MimeMessageHelper(mime, true);
		mimeHelper.setTo(order.getClient().getEmail());
		mimeHelper.setFrom(sender);
		mimeHelper.setSubject("Pedido confirmado! Código: " + order.getId());
		mimeHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeHelper.setText(htmlFromTemplateOrder(order), true);
		return mime;
	}

	private String htmlFromTemplateOrder(Order order) {
		Context context = new Context();
		context.setVariable("order", order);
		return templateEngine.process("email/orderConfirmation", context);
	}


}
