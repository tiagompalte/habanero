package com.tiagopalte.habanero.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tiagopalte.habanero.domain.Category;
import com.tiagopalte.habanero.domain.Product;
import com.tiagopalte.habanero.repositories.CategoryRepository;
import com.tiagopalte.habanero.repositories.ProductRepository;
import com.tiagopalte.habanero.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Product find(Integer id) {
		Optional<Product> category = repo.findById(id);
		return category.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName()));
	}
	
	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categories = categoryRepository.findAllById(ids);
		return repo.search(name, categories, pageRequest);
	}
	
}
