package com.tiagopalte.habanero.repositories;

import com.tiagopalte.habanero.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiagopalte.habanero.domain.Order;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Transactional(readOnly = true)
    Page<Order> findByClient(Client client, Pageable pageable);

}
