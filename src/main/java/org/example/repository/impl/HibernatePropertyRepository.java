package org.example.repository.impl;

import org.example.domain.Property;
import org.example.domain.PropertyType;
import org.example.domain.TransactionType;

import org.example.repository.interfaces.PropertyRepository;
import org.example.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HibernatePropertyRepository implements PropertyRepository {

    @Override
    public void save(Property property) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(property);
            tx.commit();
        }
    }

    @Override
    public void update(Property property) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(property);
            tx.commit();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Property property = session.get(Property.class, id);
            if (property != null) {
                session.remove(property);
            }
            tx.commit();
        }
    }

    @Override
    public java.util.Optional<Property> findById(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return java.util.Optional.ofNullable(session.get(Property.class, id));
        }
    }

    @Override
    public List<Property> findAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("select p from Property p join fetch p.agent", Property.class).list();
        }
    }

    @Override
    public List<Property> searchProperties(
            String city,
            String neighborhood,
            PropertyType propertyType,
            TransactionType transactionType,
            Integer minPrice,
            Integer maxPrice
    ) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("select p from Property p join fetch p.agent where 1=1");
            if (city != null) hql.append(" and p.city = :city");
            if (neighborhood != null) hql.append(" and p.neighborhood = :neighborhood");
            if (propertyType != null) hql.append(" and p.propertyType = :propertyType");
            if (transactionType != null) hql.append(" and p.transactionType = :transactionType");
            if (minPrice != null) hql.append(" and p.price >= :minPrice");
            if (maxPrice != null) hql.append(" and p.price <= :maxPrice");

            var query = session.createQuery(hql.toString(), Property.class);
            if (city != null) query.setParameter("city", city);
            if (neighborhood != null) query.setParameter("neighborhood", neighborhood);
            if (propertyType != null) query.setParameter("propertyType", propertyType);
            if (transactionType != null) query.setParameter("transactionType", transactionType);
            if (minPrice != null) query.setParameter("minPrice", minPrice);
            if (maxPrice != null) query.setParameter("maxPrice", maxPrice);

            return query.list();
        }
    }
}