package br.com.gabrielarsenio.loja;

import br.com.gabrielarsenio.loja.dao.EstoqueDAO;
import br.com.gabrielarsenio.loja.main.Servidor;
import br.com.gabrielarsenio.loja.model.Estoque;
import br.com.gabrielarsenio.loja.model.Produto;
import br.com.gabrielarsenio.loja.model.enums.TipoTransacaoEstoque;
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
import java.time.LocalDateTime;

/**
 * Gabriel Arsenio 26/03/2017.
 */
public class ClientEstoque {

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
    public void buscaUltimoEstoqueProduto() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Produto p = new Produto();
        p.setNome("[JUnit Test] Novo Produto");
        session.saveOrUpdate(p);

        Estoque e1 = new Estoque();
        e1.setProduto(p);
        e1.setData(LocalDateTime.now());
        e1.setTipoTransacao(TipoTransacaoEstoque.ENTRADA_MANUAL);
        e1.setQuantidadeTransacao(10.0);
        e1.setQuantidadeAtual(10.0);
        session.saveOrUpdate(e1);

        Estoque e2 = new Estoque();
        e2.setProduto(p);
        e2.setData(LocalDateTime.now());
        e2.setTipoTransacao(TipoTransacaoEstoque.ENTRADA_MANUAL);
        e2.setQuantidadeTransacao(20.0);
        e2.setQuantidadeAtual(30.0);
        session.saveOrUpdate(e2);

        Estoque e3 = new Estoque();
        e3.setProduto(p);
        e3.setData(LocalDateTime.now());
        e3.setTipoTransacao(TipoTransacaoEstoque.SAIDA_MANUAL);
        e3.setQuantidadeTransacao(-5.0);
        e3.setQuantidadeAtual(25.0);
        session.saveOrUpdate(e3);

        transaction.commit();

        Estoque estoqueAtual = (Estoque) new EstoqueDAO().buscarUltimoEstoqueProduto(p.getCodigo());

        session.close();

        Assert.assertTrue(25.0 == estoqueAtual.getQuantidadeAtual());
    }

    @Test
    public void postEstoque() {
        Estoque e = new Estoque();

        Response res = target.path("/estoque").request().post(Entity.json(e.toJson()));

        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void putEstoques() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Estoque e = new Estoque();

        session.saveOrUpdate(e);
        transaction.commit();
        session.close();

        e.setData(LocalDateTime.now());

        Response res = target.path("/estoque/" + e.getCodigo()).request().put(Entity.json(e.toJson()));

        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void deleteEstoques() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Estoque e = new Estoque();

        session.saveOrUpdate(e);
        transaction.commit();
        session.close();

        Response res = target.path("/estoque/" + e.getCodigo()).request().delete();

        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void getEstoques() {
        Response res = target.path("/estoque").request().get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void getEstoque() {
        Response res = target.path("/estoque/1").request().get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }
}
