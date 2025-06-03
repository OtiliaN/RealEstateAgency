package org.example.service;

import org.example.domain.Property;
import org.example.repository.interfaces.PropertyRepository;
import org.example.utils.PropertyObserver;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class AgentService {
    private final PropertyRepository propertyRepo;
    private final List<PropertyObserver> observers = new CopyOnWriteArrayList<>();

    public AgentService(PropertyRepository propertyRepo) {
        this.propertyRepo = propertyRepo;
    }

    public void addObserver(PropertyObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(PropertyObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        for (PropertyObserver obs : observers) {
            obs.onPropertyListChanged();
        }
    }

    public void addProperty(Property property) {
        propertyRepo.save(property);
        notifyObservers();
    }

    public void updateProperty(Property property) {
        propertyRepo.update(property);
        notifyObservers();
    }

    public void deleteProperty(Integer id) {
        propertyRepo.delete(id);
        notifyObservers();
    }
    public Optional<Property> findPropertyById(Integer id) {
        return propertyRepo.findById(id);
    }

    public List<Property> findAllProperties() {
        return propertyRepo.findAll();
    }
}