package br.com.gabrielarsenio.loja.main;

import br.com.gabrielarsenio.loja.model.Produto;
import br.com.gabrielarsenio.loja.util.HibernateUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URI;

/**
 * Gabriel Arsenio 24/03/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {

//        URI uri = URI.create("http://localhost:3000/");
//
//        ResourceConfig recursos = new ResourceConfig().packages("br.com.gabrielarsenio.loja");
//
//        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, recursos);
//
//        System.out.println("Servidor ON");
//        System.in.read();
//        server.stop();

        Produto p = new Produto();
        p.setNome("Aeeeeeee");

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(p);
        session.getTransaction().commit();

        session.close();

        sessionFactory.close();

        System.out.println("FIM");
    }
}