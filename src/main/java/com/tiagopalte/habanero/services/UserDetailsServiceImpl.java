package com.tiagopalte.habanero.services;

import com.tiagopalte.habanero.domain.Client;
import com.tiagopalte.habanero.repositories.ClientRepository;
import com.tiagopalte.habanero.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Client client = repository.findByEmail(email);

        if(client == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSpringSecurity(client.getId(), client.getEmail(), client.getPassword(), client.getProfiles());
    }

}
