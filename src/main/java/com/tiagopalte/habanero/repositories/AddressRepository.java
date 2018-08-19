package com.tiagopalte.habanero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiagopalte.habanero.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
