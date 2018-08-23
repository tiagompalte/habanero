package com.tiagopalte.habanero.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.tiagopalte.habanero.domain.Client;
import com.tiagopalte.habanero.domain.enums.ClientType;
import com.tiagopalte.habanero.dto.ClientNewDTO;
import com.tiagopalte.habanero.repositories.ClientRepository;
import com.tiagopalte.habanero.resources.exceptions.FieldMessage;
import com.tiagopalte.habanero.services.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {
	
	@Autowired
	private ClientRepository repo;
	
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getType() == null) {
			list.add(new FieldMessage("type", "Tipo não pode ser nulo"));
		}
		
		if(objDto.getType().equals(ClientType.NATURAL_PERSON.getCode()) && !BR.isValidCPF(objDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CPF inválido"));
		}
		
		if(objDto.getType().equals(ClientType.LEGAL_PERSON.getCode()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CNPJ inválido"));
		}
		
		Client sameEmail = repo.findByEmail(objDto.getEmail());
		if(sameEmail != null) {
			list.add(new FieldMessage("email", "E-mail já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}