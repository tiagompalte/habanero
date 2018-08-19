package com.tiagopalte.habanero.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagopalte.habanero.domain.Client;
import com.tiagopalte.habanero.repositories.ClientRepository;
import com.tiagopalte.habanero.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repo;
	
	public Client find(Integer id) {
		Optional<Client> client = repo.findById(id);
		return client.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
	
}
