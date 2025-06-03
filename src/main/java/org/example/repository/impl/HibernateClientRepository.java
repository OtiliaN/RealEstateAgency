package org.example.repository.impl;

import org.example.domain.Client;
import org.example.repository.interfaces.ClientRepository;
import org.example.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HibernateClientRepository implements ClientRepository {

    @Override
    public void save(Client client) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(client);
            tx.commit();
        }
    }

    @Override
    public void update(Client client) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(client);
            tx.commit();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Client client = session.get(Client.class, id);
            if (client != null) {
                session.remove(client);
            }
            tx.commit();
        }
    }

    @Override
    public Optional<Client> findById(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Client.class, id));
        }
    }

    @Override
    public List<Client> findAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from Client", Client.class).list();
        }
    }

    @Override
    public Optional<Client> findByUsername(String username) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Client client = session.createQuery("from Client where username = :username", Client.class)
                    .setParameter("username", username)
                    .uniqueResult();
            return Optional.ofNullable(client);
        }
    }
}