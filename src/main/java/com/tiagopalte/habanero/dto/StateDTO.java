package com.tiagopalte.habanero.dto;

import com.tiagopalte.habanero.domain.State;

import java.io.Serializable;

public class StateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public StateDTO() {}

    public StateDTO(State state) {
        setId(state.getId());
        setName(state.getName());
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
