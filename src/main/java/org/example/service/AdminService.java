package org.example.service;

import org.example.domain.Agent;
import org.example.repository.interfaces.AgentRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class AdminService {
    private final AgentRepository agentRepo;

    public AdminService(AgentRepository agentRepo) {
        this.agentRepo = agentRepo;
    }

    public void addAgent(Agent agent) {
        agent.setPassword(BCrypt.hashpw(agent.getPassword(), BCrypt.gensalt()));
        agentRepo.save(agent);
    }

    public void updateAgent(Agent agent) {
        agentRepo.update(agent);
    }

    public void deleteAgent(Integer id) {
        agentRepo.delete(id);
    }

    public Optional<Agent> findAgentById(Integer id) {
        return agentRepo.findById(id);
    }

    public Optional<Agent> findAgentByUsername(String username) {
        return agentRepo.findByUsername(username);
    }

    public List<Agent> findAllAgents() {
        return agentRepo.findAll();
    }
}