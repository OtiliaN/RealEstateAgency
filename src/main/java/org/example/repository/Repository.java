package org.example.repository;

import org.example.domain.Entity;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity<ID>, ID> {
    void save(T entity);
    void update(T entity);
    void delete(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();
}
