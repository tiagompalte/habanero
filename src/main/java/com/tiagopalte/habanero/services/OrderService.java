package com.tiagopalte.habanero.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiagopalte.habanero.domain.Order;
import com.tiagopalte.habanero.domain.OrderItem;
import com.tiagopalte.habanero.domain.PaymentSlip;
import com.tiagopalte.habanero.domain.enums.PaymentState;
import com.tiagopalte.habanero.repositories.OrderItemRepository;
import com.tiagopalte.habanero.repositories.OrderRepository;
import com.tiagopalte.habanero.repositories.PaymentRepository;
import com.tiagopalte.habanero.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private PaymentSlipService paymentSlipService;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ClientService clientService;
	
	public Order find(Integer id) {
		Optional<Order> order = repo.findById(id);
		return order.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order order) {
		
		order.setId(null);
		order.setInstant(new Date());
		order.setClient(clientService.find(order.getClient().getId()));
		order.getPayment().setState(PaymentState.SETTLED);
		order.getPayment().setOrder(order);
		
		if(order.getPayment() instanceof PaymentSlip) {
			PaymentSlip slip = (PaymentSlip) order.getPayment();
			paymentSlipService.fillPaymentSlip(slip, order.getInstant());
		}
		
		order = repo.save(order);
		paymentRepository.save(order.getPayment());
		
		if(order.getItems() != null && !order.getItems().isEmpty()) {
			
			for(OrderItem item : order.getItems()) {
				item.setDiscount(0.0);
				item.setProduct(productService.find(item.getProduct().getId()));
				item.setPrice(item.getProduct().getPrice());
				item.setOrder(order);
			}
			
			orderItemRepository.saveAll(order.getItems());
		}
		
		return order;
	}
	
}
