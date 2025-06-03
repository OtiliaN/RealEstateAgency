package org.example.service;

import org.example.domain.Property;
import org.example.domain.PropertyType;
import org.example.domain.TransactionType;
import org.example.repository.interfaces.PropertyRepository;

import java.util.List;

public class PropertyService {
    private final PropertyRepository propertyRepo;

    public PropertyService(PropertyRepository propertyRepo) {
        this.propertyRepo = propertyRepo;
    }

    public List<Property> searchProperties(
            String city,
            String neighborhood,
            PropertyType propertyType,
            TransactionType transactionType,
            Integer minPrice,
            Integer maxPrice
    ) {
        return propertyRepo.searchProperties(city, neighborhood, propertyType, transactionType, minPrice, maxPrice);
    }

    public List<Property> findAllProperties() {
        return propertyRepo.findAll();
    }
}