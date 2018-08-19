package com.tiagopalte.habanero.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagopalte.habanero.domain.Order;
import com.tiagopalte.habanero.repositories.OrderRepository;
import com.tiagopalte.habanero.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;
	
	public Order find(Integer id) {
		Optional<Order> category = repo.findById(id);
		return category.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Order.class.getName()));
	}
	
}
