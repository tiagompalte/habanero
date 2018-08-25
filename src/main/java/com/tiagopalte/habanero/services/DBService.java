package com.tiagopalte.habanero.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagopalte.habanero.domain.Address;
import com.tiagopalte.habanero.domain.CardPayment;
import com.tiagopalte.habanero.domain.Category;
import com.tiagopalte.habanero.domain.City;
import com.tiagopalte.habanero.domain.Client;
import com.tiagopalte.habanero.domain.Order;
import com.tiagopalte.habanero.domain.OrderItem;
import com.tiagopalte.habanero.domain.Payment;
import com.tiagopalte.habanero.domain.PaymentSlip;
import com.tiagopalte.habanero.domain.Product;
import com.tiagopalte.habanero.domain.State;
import com.tiagopalte.habanero.domain.enums.ClientType;
import com.tiagopalte.habanero.domain.enums.PaymentState;
import com.tiagopalte.habanero.repositories.AddressRepository;
import com.tiagopalte.habanero.repositories.CategoryRepository;
import com.tiagopalte.habanero.repositories.CityRepository;
import com.tiagopalte.habanero.repositories.ClientRepository;
import com.tiagopalte.habanero.repositories.OrderItemRepository;
import com.tiagopalte.habanero.repositories.OrderRepository;
import com.tiagopalte.habanero.repositories.PaymentRepository;
import com.tiagopalte.habanero.repositories.ProductRepository;
import com.tiagopalte.habanero.repositories.StateRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoryRepository categoryRepository;	
	@Autowired
	private ProductRepository productRepository;		
	@Autowired
	private CityRepository cityRepository;	
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");	
		Category cat3 = new Category(null, "Came mesa e banho");	
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jarginagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");
				
		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Mesa de escritório", 300.00);
		Product p5 = new Product(null, "Toalha", 50.00);
		Product p6 = new Product(null, "Colcha", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Roçadeira", 800.00);
		Product p9 = new Product(null, "Abajour", 100.00);
		Product p10 = new Product(null, "Pendente", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);
		
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));
		
		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		State mg = new State(null, "Minas Gerais");
		State sp = new State(null, "São Paulo");
		
		City c1 = new City(null,  "Uberlândia", mg);
		City c2 = new City(null, "São Paulo", sp);
		City c3 = new City(null, "Campinas", sp);
		
		mg.getCities().addAll(Arrays.asList(c1));
		sp.getCities().addAll(Arrays.asList(c2, c3));		
		
		stateRepository.saveAll(Arrays.asList(mg, sp));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.NATURAL_PERSON);
		
		cli1.getPhones().addAll(Arrays.asList("27363323", "93838393"));
		
		Address address1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Address address2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getAddresses().addAll(Arrays.asList(address1, address2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(address1, address2));
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Order order1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, address1);
		Order order2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, address2);
		
		Payment pagto1 = new CardPayment(null, PaymentState.SETTLED, order1, 6);
		order1.setPayment(pagto1);
		
		Payment pagto2 = new PaymentSlip(null, PaymentState.PENDING, order2, sdf.parse("20/10/2017 00:00"), null);
		order2.setPayment(pagto2);
		
		cli1.getOrders().addAll(Arrays.asList(order1, order2));
				
		orderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		OrderItem item1 = new OrderItem(order1, p1, 0.00, 1, 2000.00);
		OrderItem item2 = new OrderItem(order1, p3, 0.00, 2, 80.00);
		OrderItem item3 = new OrderItem(order2, p2, 100.00, 1, 800.00);
		
		order1.getItems().addAll(Arrays.asList(item1, item2));
		order2.getItems().addAll(Arrays.asList(item3));
		
		p1.getItems().addAll(Arrays.asList(item1));
		p2.getItems().addAll(Arrays.asList(item3));
		p3.getItems().addAll(Arrays.asList(item2));
		
		orderItemRepository.saveAll(Arrays.asList(item1, item2, item3));	
	}

}
