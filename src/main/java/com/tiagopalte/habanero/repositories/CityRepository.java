package com.tiagopalte.habanero.repositories;

import com.tiagopalte.habanero.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiagopalte.habanero.domain.City;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query("SELECT c FROM City c WHERE c.state.id = :stateId ORDER BY c.name")
    List<City> findCities(@Param("stateId") Integer stateId);

}
