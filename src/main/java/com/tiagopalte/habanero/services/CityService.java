package com.tiagopalte.habanero.services;

import com.tiagopalte.habanero.domain.City;
import com.tiagopalte.habanero.domain.State;
import com.tiagopalte.habanero.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository repo;

    public List<City> findByState(Integer stateId) {
        return repo.findCities(stateId);
    }

}
