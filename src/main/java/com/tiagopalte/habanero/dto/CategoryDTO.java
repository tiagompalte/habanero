package com.tiagopalte.habanero.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.tiagopalte.habanero.domain.Category;

public class CategoryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String name;
	
	public CategoryDTO() {		
	}
	
	public CategoryDTO(Category category) {
		setId(category.getId());
		setName(category.getName());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
