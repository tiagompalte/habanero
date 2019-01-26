package com.tiagopalte.habanero.resources;

import java.net.URI;

import javax.validation.Valid;

import com.tiagopalte.habanero.domain.Client;
import com.tiagopalte.habanero.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tiagopalte.habanero.domain.Order;
import com.tiagopalte.habanero.services.OrderService;

@RestController
@RequestMapping(value="/orders")
public class OrderResource {
	
	@Autowired
	private OrderService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Order order = service.find(id);		
		return ResponseEntity.ok(order);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Order order) {
		order = service.insert(order);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(order.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<Order>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="instant") String orderBy,
			@RequestParam(value="direction", defaultValue="DESC") String direction) {
		Page<Order> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok(list);
	}
	
}
