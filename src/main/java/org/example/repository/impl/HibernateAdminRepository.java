package org.example.repository.impl;

import org.example.domain.Admin;
import org.example.repository.interfaces.AdminRepository;
import org.example.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class HibernateAdminRepository implements AdminRepository {
    @Override
    public void save(Admin admin) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(admin);
            tx.commit();
        }
    }

    @Override
    public void update(Admin admin) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(admin);
            tx.commit();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Admin admin = session.get(Admin.class, id);
            if (admin != null) {
                session.remove(admin);
            }
            tx.commit();
        }
    }

    @Override
    public Optional<Admin> findById(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Admin.class, id));
        }
    }

    @Override
    public List<Admin> findAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from Admin", Admin.class).list();
        }
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Admin admin = session.createQuery("from Admin where username = :username", Admin.class)
                    .setParameter("username", username)
                    .uniqueResult();
            return Optional.ofNullable(admin);
        }
    }
}
