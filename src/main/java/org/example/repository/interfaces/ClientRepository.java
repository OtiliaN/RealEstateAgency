package org.example.repository.interfaces;


import org.example.domain.Client;
import org.example.repository.Repository;

import java.util.Optional;

public interface ClientRepository extends Repository<Client, Integer> {
    Optional<Client> findByUsername(String username);
}
