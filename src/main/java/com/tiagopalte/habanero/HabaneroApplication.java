package com.tiagopalte.habanero;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

@SpringBootApplication
public class HabaneroApplication implements CommandLineRunner {
	
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

	public static void main(String[] args) {
		SpringApplication.run(HabaneroApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");		
				
		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));
		
		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		
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
