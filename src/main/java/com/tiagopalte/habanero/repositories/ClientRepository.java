package com.tiagopalte.habanero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiagopalte.habanero.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
