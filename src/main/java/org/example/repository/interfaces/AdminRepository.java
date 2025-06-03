package org.example.repository.interfaces;

import org.example.domain.Admin;
import org.example.repository.Repository;

import java.util.Optional;

public interface AdminRepository extends Repository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);

}
