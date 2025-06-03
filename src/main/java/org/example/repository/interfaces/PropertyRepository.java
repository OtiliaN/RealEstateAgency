package org.example.repository.interfaces;

import org.example.domain.Agent;
import org.example.domain.Property;
import org.example.domain.PropertyType;
import org.example.domain.TransactionType;
import org.example.repository.Repository;

import java.util.List;

public interface PropertyRepository extends Repository<Property, Integer> {
    /*
    Search for properties based on various criteria. Each parameter is optional
    * */
    List<Property> searchProperties(
            String city,
            String neighborhood,
            PropertyType propertyType,
            TransactionType transactionType,
            Integer minPrice,
            Integer maxPrice
    );
}
