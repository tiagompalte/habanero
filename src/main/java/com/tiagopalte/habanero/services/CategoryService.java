package com.tiagopalte.habanero.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagopalte.habanero.domain.Category;
import com.tiagopalte.habanero.repositories.CategoryRepository;
import com.tiagopalte.habanero.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public Category find(Integer id) {
		Optional<Category> category = repo.findById(id);
		return category.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Category.class.getName()));
	}
	
}
