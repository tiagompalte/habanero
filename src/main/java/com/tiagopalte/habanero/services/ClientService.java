package com.tiagopalte.habanero.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.tiagopalte.habanero.domain.enums.Profile;
import com.tiagopalte.habanero.security.UserSpringSecurity;
import com.tiagopalte.habanero.services.exceptions.AuthorizationException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiagopalte.habanero.domain.Address;
import com.tiagopalte.habanero.domain.City;
import com.tiagopalte.habanero.domain.Client;
import com.tiagopalte.habanero.domain.enums.ClientType;
import com.tiagopalte.habanero.dto.ClientDTO;
import com.tiagopalte.habanero.dto.ClientNewDTO;
import com.tiagopalte.habanero.repositories.AddressRepository;
import com.tiagopalte.habanero.repositories.ClientRepository;
import com.tiagopalte.habanero.services.exceptions.DataIntegrityException;
import com.tiagopalte.habanero.services.exceptions.ObjectNotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repo;
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;
	
	public Client find(Integer id) {
		UserSpringSecurity userSpringSecurity = UserService.authenticated();
		if(userSpringSecurity == null || !userSpringSecurity.hasRole(Profile.ADMIN) && !id.equals(userSpringSecurity.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Client> client = repo.findById(id);
		return client.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
	
	@Transactional
	public Client insert(Client client) {
		client.setId(null);
		client = repo.save(client);
		addressRepository.saveAll(client.getAddresses());
		return client;
	}
	
	public Client update(Client client) {
		Client oldClient = find(client.getId());
		Client newClient = updateData(oldClient, client);
		return repo.save(newClient);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir, porque há pedidos relacionadas");
		}		
	}

	public List<Client> findAll() {
		return repo.findAll();
	}

	public Client findByEamil(String email) {

		UserSpringSecurity userSpringSecurity = UserService.authenticated();

		if(userSpringSecurity == null || !userSpringSecurity.hasRole(Profile.ADMIN) && !email.equals(userSpringSecurity.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Client client = repo.findByEmail(email);
		if(client == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + userSpringSecurity.getId()
													+ ", Tipo: " + Client.class.getName());
		}
		return client;
	}
	
	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Client fromDTO(ClientDTO dto) {
		return new Client(dto.getId(), dto.getName(), dto.getEmail(), null, null, null);
	}
	
	public Client fromDTO(ClientNewDTO dto) {
		
		Client client = new Client(null, dto.getName(), dto.getEmail(), dto.getCpfOrCnpj(), ClientType.toEnum(dto.getType()), passwordEncoder.encode((dto.getPassword())));
		City city = new City(dto.getCityId(), null, null);
		Address address = new Address(null, dto.getPublicPlace(), dto.getNumber(), dto.getComplement(), dto.getNeighborhood(), dto.getCep(), client, city);
		client.getAddresses().add(address);
		client.getPhones().add(dto.getPhone1());
		if(dto.getPhone2() != null) {
			client.getPhones().add(dto.getPhone2());
		}
		if(dto.getPhone3() != null) {
			client.getPhones().add(dto.getPhone3());
		}
		return client;
	}
	
	private Client updateData(Client databaseObj, Client obj) {
		Client newObj = databaseObj;
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
		return newObj;
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {

		UserSpringSecurity userSpringSecurity = UserService.authenticated();
		if(userSpringSecurity == null) {
			throw new AuthorizationException("Acesso negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + userSpringSecurity.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
