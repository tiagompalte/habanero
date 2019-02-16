package com.tiagopalte.habanero.services;

import com.tiagopalte.habanero.domain.City;
import com.tiagopalte.habanero.domain.State;
import com.tiagopalte.habanero.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateRepository repo;

    public List<State> findAll() {
        return repo.findAllByOrderByName();
    }

}
