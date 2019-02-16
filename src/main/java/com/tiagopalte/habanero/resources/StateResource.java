package com.tiagopalte.habanero.resources;

import com.tiagopalte.habanero.domain.City;
import com.tiagopalte.habanero.domain.State;
import com.tiagopalte.habanero.dto.CityDTO;
import com.tiagopalte.habanero.dto.StateDTO;
import com.tiagopalte.habanero.services.CityService;
import com.tiagopalte.habanero.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/states")
public class StateResource {

    @Autowired
    private StateService service;

    @Autowired
    private CityService cityService;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<StateDTO>> findAll() {
        List<State> states = service.findAll();
        List<StateDTO> stateDTOS = states.stream().map(s -> new StateDTO(s)).collect(Collectors.toList());
        return ResponseEntity.ok(stateDTOS);
    }

    @RequestMapping(value = "/{stateId}/cities", method = RequestMethod.GET)
    public ResponseEntity<List<CityDTO>> findCities(@PathVariable Integer stateId) {
        List<City> cityList = cityService.findByState(stateId);
        List<CityDTO> cityDTOS = cityList.stream().map(c -> new CityDTO(c)).collect(Collectors.toList());
        return ResponseEntity.ok(cityDTOS);
    }

}
