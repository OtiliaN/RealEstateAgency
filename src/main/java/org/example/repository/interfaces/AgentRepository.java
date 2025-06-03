package org.example.repository.interfaces;

import org.example.domain.Agent;
import org.example.repository.Repository;

import java.util.Optional;

public interface AgentRepository extends Repository<Agent, Integer> {
    Optional<Agent> findByUsername(String username);
}
