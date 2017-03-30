package br.com.gabrielarsenio.loja;

import br.com.gabrielarsenio.loja.main.Servidor;
import br.com.gabrielarsenio.loja.model.CategoriaProduto;
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
public class ClientCategoriaProduto {

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
    public void postCategoriaProduto() {
        CategoriaProduto cp = new CategoriaProduto();
        cp.setNome("[TESTE JUnit] CategoriaProduto INSERIDO");

        Response res = target.path("/categoria-produto").request().post(Entity.json(cp.toJson()));

        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void putCategoriaProduto() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CategoriaProduto cp = new CategoriaProduto();
        cp.setNome("[TESTE JUnit] Categoria Produto SALVO");

        session.saveOrUpdate(cp);
        transaction.commit();
        session.close();

        cp.setNome("[TESTE JUnit] Categoria Produto ALTERADO");

        Response res = target.path("/categoria-produto/" + cp.getCodigo()).request().put(Entity.json(cp.toJson()));

        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void deleteCategoriaProduto() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CategoriaProduto cp = new CategoriaProduto();
        cp.setNome("[TESTE JUnit] Produto SALVO");

        session.saveOrUpdate(cp);
        transaction.commit();
        session.close();

        Response res = target.path("/categoria-produto/" + cp.getCodigo()).request().delete();

        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void getCategoriasProduto() {
        Response res = target.path("/categoria-produto").request().get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void getCategoriaProduto() {
        Response res = target.path("/categoria-produto/1").request().get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }
}
