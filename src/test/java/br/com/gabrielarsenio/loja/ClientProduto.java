package br.com.gabrielarsenio.loja;

import br.com.gabrielarsenio.loja.main.Servidor;
import br.com.gabrielarsenio.loja.model.Produto;
import br.com.gabrielarsenio.loja.util.HibernateUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public class ClientProduto {

    private static HttpServer server;
    private static Client client;
    private static WebTarget target;

    @Before
    public void iniciaAplicacao() {
        server = Servidor.startHttpServer();
        client = ClientBuilder.newClient();
        target = client.target(Servidor.BASE_URI);
    }

    @After
    public void encerraAplicacao() {
        server.stop();
    }

    @Test
    public void postProdutos() {
        Produto p = new Produto();
        p.setNome("[TESTE JUnit] Produto INSERIDO");

        Response res = target.path("/produtos").request().post(Entity.json(p.toJson()));

        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void putProdutos() {
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

        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void deleteProdutos() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Produto p = new Produto();
        p.setNome("[TESTE JUnit] Produto SALVO");

        session.saveOrUpdate(p);
        transaction.commit();
        session.close();

        Response res = target.path("/produtos/" + p.getCodigo()).request().delete();

        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void getProdutos() {
        Response res = target.path("/produtos").request().get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void getProduto() {
        Response res = target.path("/produtos/1").request().get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }
}
