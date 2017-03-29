package br.com.gabrielarsenio.loja;

import br.com.gabrielarsenio.loja.main.Servidor;
import br.com.gabrielarsenio.loja.model.CategoriaProduto;
import br.com.gabrielarsenio.loja.util.HibernateUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.ClientFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public class ClientCategoriaProduto {

    private static HttpServer server;

    @BeforeAll
    static void iniciaAplicacao() {
        server = Servidor.startHttpServer();
    }

    @AfterAll
    static void encerraAplicacao() {
        server.stop();
    }

    @Test
    void postCategoriaProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        CategoriaProduto cp = new CategoriaProduto();
        cp.setNome("[TESTE JUnit] CategoriaProduto INSERIDO");

        Response res = target.path("/categoria-produto").request().post(Entity.json(cp.toJson()));

        Assertions.assertEquals(Response.Status.CREATED, res.getStatusInfo());
    }

    @Test
    void putCategoriaProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);
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

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    void deleteCategoriaProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CategoriaProduto cp = new CategoriaProduto();
        cp.setNome("[TESTE JUnit] Produto SALVO");

        session.saveOrUpdate(cp);
        transaction.commit();
        session.close();

        Response res = target.path("/categoria-produto/" + cp.getCodigo()).request().delete();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    void getCategoriasProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/categoria-produto").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    void getCategoriaProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/categoria-produto/1").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }
}
