package br.com.gabrielarsenio.loja;

import br.com.gabrielarsenio.loja.main.Servidor;
import br.com.gabrielarsenio.loja.model.Produto;
import br.com.gabrielarsenio.loja.util.HibernateUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import javax.ws.rs.client.ClientFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public class ClientProduto {

    private static HttpServer server;

    @BeforeAll
    public static void iniciaAplicacao() {
        server = Servidor.startHttpServer();
    }

    @AfterAll
    public static void encerraAplicacao() {
        server.stop();
    }

    @Test
    public void postProdutos() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Produto p = new Produto();
        p.setNome("[TESTE JUnit] Produto INSERIDO");

        Response res = target.path("/produtos").request().post(Entity.json(p.toJson()));

        Assertions.assertEquals(Response.Status.CREATED, res.getStatusInfo());
    }

    @Test
    public void putProdutos() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Produto p = new Produto();
        p.setNome("[TESTE JUnit] Produto SALVO");

        session.saveOrUpdate(p);
        transaction.commit();
        session.close();

        p.setNome("[TESTE JUnit] Produto ALTERADO");

        Response res = target.path("/produtos/" + p.getCodigo()).request().put(Entity.json(p.toJson()));

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void deleteProdutos() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Produto p = new Produto();
        p.setNome("[TESTE JUnit] Produto SALVO");

        session.saveOrUpdate(p);
        transaction.commit();
        session.close();

        Response res = target.path("/produtos/" + p.getCodigo()).request().delete();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void getProdutos() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/produtos").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void getProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/produtos/1").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }
}
