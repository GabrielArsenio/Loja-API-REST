package br.com.gabrielarsenio.loja;

import br.com.gabrielarsenio.loja.dao.UsuarioDAO;
import br.com.gabrielarsenio.loja.main.Servidor;
import br.com.gabrielarsenio.loja.model.Usuario;
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
 * Gabriel Arsenio 26/03/2017.
 */

public class ClientUsuario {

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
    public void buscarUsuario() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Usuario usr = new Usuario();
        usr.setUsuario("JUnit");
        usr.setSenha("tinUJ");

        session.saveOrUpdate(usr);
        transaction.commit();

        Usuario usrEncontrado = new UsuarioDAO().buscarUsuario(usr.getUsuario(), usr.getSenha());

        session.close();

        Assert.assertEquals(usr.getUsuario(), usrEncontrado.getUsuario());
    }

    @Test
    public void postUsuarios() {
        Usuario p = new Usuario();
        p.setUsuario("[TESTE JUnit] Usuario INSERIDO");

        Response res = target.path("/usuario").request().post(Entity.json(p.toJson()));

        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void putUsuarios() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Usuario p = new Usuario();
        p.setUsuario("[TESTE JUnit] Usuario SALVO");

        session.saveOrUpdate(p);
        transaction.commit();
        session.close();

        p.setUsuario("[TESTE JUnit] Usuario ALTERADO");

        Response res = target.path("/usuario/" + p.getCodigo()).request().put(Entity.json(p.toJson()));

        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void deleteUsuarios() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Usuario p = new Usuario();
        p.setUsuario("[TESTE JUnit] Usuario SALVO");

        session.saveOrUpdate(p);
        transaction.commit();
        session.close();

        Response res = target.path("/usuario/" + p.getCodigo()).request().delete();

        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void getUsuarios() {
        Response res = target.path("/usuario").request().get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }

    @Test
    public void getUsuario() {
        Response res = target.path("/usuario/1").request().get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), res.getStatusInfo().getStatusCode());
    }
}
