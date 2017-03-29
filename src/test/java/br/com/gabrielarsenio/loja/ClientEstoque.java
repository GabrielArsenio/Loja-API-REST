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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.ClientFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

/**
 * Gabriel Arsenio 26/03/2017.
 */
public class ClientEstoque {
    
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
    void buscaUltimoEstoqueProduto() {
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

        Assertions.assertTrue(25.0 == estoqueAtual.getQuantidadeAtual());
    }

    @Test
    void postEstoque() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Estoque e = new Estoque();

        Response res = target.path("/estoque").request().post(Entity.json(e.toJson()));

        Assertions.assertEquals(Response.Status.CREATED, res.getStatusInfo());
    }

    @Test
    void putEstoques() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Estoque e = new Estoque();

        session.saveOrUpdate(e);
        transaction.commit();
        session.close();

        e.setData(LocalDateTime.now());

        Response res = target.path("/estoque/" + e.getCodigo()).request().put(Entity.json(e.toJson()));

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    void deleteEstoques() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Estoque e = new Estoque();

        session.saveOrUpdate(e);
        transaction.commit();
        session.close();

        Response res = target.path("/estoque/" + e.getCodigo()).request().delete();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    void getEstoques() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/estoque").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    void getEstoque() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/estoque/1").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }
}
