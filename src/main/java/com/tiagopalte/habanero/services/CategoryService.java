package com.tiagopalte.habanero.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tiagopalte.habanero.domain.Category;
import com.tiagopalte.habanero.dto.CategoryDTO;
import com.tiagopalte.habanero.repositories.CategoryRepository;
import com.tiagopalte.habanero.services.exceptions.DataIntegrityException;
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

	public Category insert(Category category) {
		category.setId(null);
		return repo.save(category);		
	}

	public Category update(Category category) {
		Category oldCategory = find(category.getId());
		Category newCategory = updateData(oldCategory, category);
		return repo.save(newCategory);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}		
	}

	public List<Category> findAll() {
		return repo.findAll();
	}
	
	public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Category fromDTO(CategoryDTO dto) {
		return new Category(dto.getId(), dto.getName());
	}
	
	private Category updateData(Category databaseObj, Category obj) {
		Category newObj = databaseObj;
		newObj.setName(obj.getName());
		return newObj;
	}
	
}
