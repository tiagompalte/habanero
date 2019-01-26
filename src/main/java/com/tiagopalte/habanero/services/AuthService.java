package com.tiagopalte.habanero.services;

import com.tiagopalte.habanero.domain.Client;
import com.tiagopalte.habanero.repositories.ClientRepository;
import com.tiagopalte.habanero.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email) {

        Client client =  clientRepository.findByEmail(email);

        if(client == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        client.setPassword(bCryptPasswordEncoder.encode(newPass));

        clientRepository.save(client);

        emailService.sendNewPasswordEmail(client, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        switch (opt) {
            case 0:
                return (char) (random.nextInt(10) + 48);
            case 1:
                return (char) (random.nextInt(26) + 65);
            default:
                return (char) (random.nextInt(26) + 97);
        }
    }

}
