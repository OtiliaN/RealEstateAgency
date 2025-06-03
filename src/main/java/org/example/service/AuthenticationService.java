package org.example.service;

import org.example.domain.Admin;
import org.example.domain.Agent;
import org.example.domain.Client;
import org.example.repository.interfaces.AdminRepository;
import org.example.repository.interfaces.AgentRepository;
import org.example.repository.interfaces.ClientRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthenticationService {
    private final AdminRepository adminRepo;
    private final AgentRepository agentRepo;
    private final ClientRepository clientRepo;

    public AuthenticationService(AdminRepository adminRepo, AgentRepository agentRepo, ClientRepository clientRepo) {
        this.adminRepo = adminRepo;
        this.agentRepo = agentRepo;
        this.clientRepo = clientRepo;
    }

    public Optional<Admin> loginAdmin(String username, String password) {
        return adminRepo.findByUsername(username)
                .filter(admin -> BCrypt.checkpw(password, admin.getPassword()));
    }

    public Optional<Agent> loginAgent(String username, String password) {
        return agentRepo.findByUsername(username)
                .filter(agent -> BCrypt.checkpw(password, agent.getPassword()));
    }

    public Optional<Client> loginClient(String username, String password) {
        return clientRepo.findByUsername(username)
                .filter(client -> BCrypt.checkpw(password, client.getPassword()));
    }

    public void logout() {

    }
}