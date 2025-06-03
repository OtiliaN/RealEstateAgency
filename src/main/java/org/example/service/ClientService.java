package org.example.service;

import org.example.domain.Client;
import org.example.repository.interfaces.ClientRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientRepository clientRepo;

    public ClientService(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public void signUp(Client client) {
        client.setPassword(BCrypt.hashpw(client.getPassword(), BCrypt.gensalt()));
        clientRepo.save(client);
    }

    public Optional<Client> findByUsername(String username) {
        return clientRepo.findByUsername(username);
    }

    public List<Client> findAllClients() {
        return clientRepo.findAll();
    }
}