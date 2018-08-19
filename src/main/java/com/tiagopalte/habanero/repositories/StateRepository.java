package com.tiagopalte.habanero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiagopalte.habanero.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
