package com.tiagopalte.habanero.dto;

import com.tiagopalte.habanero.domain.City;

import java.io.Serializable;

public class CityDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public CityDTO() {
    }

    public CityDTO(City city) {
        setId(city.getId());
        setName(city.getName());
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
