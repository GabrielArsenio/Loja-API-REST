package br.com.gabrielarsenio.loja.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }

        return sessionFactory;
    }
}
