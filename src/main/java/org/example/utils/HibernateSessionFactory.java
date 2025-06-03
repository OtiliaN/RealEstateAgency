package org.example.utils;

import org.example.domain.Admin;
import org.example.domain.Agent;
import org.example.domain.Client;
import org.example.domain.Property;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = createNewSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory createNewSessionFactory() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Agent.class)
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Admin.class)
                .addAnnotatedClass(Property.class);
          return configuration.buildSessionFactory();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
