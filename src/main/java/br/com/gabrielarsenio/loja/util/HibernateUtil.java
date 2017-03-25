package br.com.gabrielarsenio.loja.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure(new File("src/main/conf/hibernate.cfg.xml")).buildSessionFactory();
        }

        return sessionFactory;
    }
}
