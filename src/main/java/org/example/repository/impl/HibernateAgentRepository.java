package org.example.repository.impl;

import org.example.domain.Agent;
import org.example.repository.interfaces.AgentRepository;
import org.example.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HibernateAgentRepository implements AgentRepository {

    @Override
    public void save(Agent agent) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(agent);
            tx.commit();
        }
    }

    @Override
    public void update(Agent agent) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(agent);
            tx.commit();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Agent agent = session.get(Agent.class, id);
            if (agent != null) {
                session.remove(agent);
            }
            tx.commit();
        }
    }

    @Override
    public Optional<Agent> findById(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Agent.class, id));
        }
    }

    @Override
    public List<Agent> findAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from Agent", Agent.class).list();
        }
    }

    @Override
    public Optional<Agent> findByUsername(String username) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Agent agent = session.createQuery("from Agent where username = :username", Agent.class)
                    .setParameter("username", username)
                    .uniqueResult();
            return Optional.ofNullable(agent);
        }
    }
}