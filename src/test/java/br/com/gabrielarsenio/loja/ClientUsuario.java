package br.com.gabrielarsenio.loja;

import br.com.gabrielarsenio.loja.dao.UsuarioDAO;
import br.com.gabrielarsenio.loja.main.Servidor;
import br.com.gabrielarsenio.loja.model.Usuario;
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
 * Gabriel Arsenio 26/03/2017.
 */

public class ClientUsuario {
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

        Assertions.assertEquals(usr.getUsuario(), usrEncontrado.getUsuario());
    }

    @Test
    public void postUsuarios() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Usuario p = new Usuario();
        p.setUsuario("[TESTE JUnit] Usuario INSERIDO");

        Response res = target.path("/usuario").request().post(Entity.json(p.toJson()));

        Assertions.assertEquals(Response.Status.CREATED, res.getStatusInfo());
    }

    @Test
    public void putUsuarios() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);
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

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void deleteUsuarios() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Usuario p = new Usuario();
        p.setUsuario("[TESTE JUnit] Usuario SALVO");

        session.saveOrUpdate(p);
        transaction.commit();
        session.close();

        Response res = target.path("/usuario/" + p.getCodigo()).request().delete();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void getUsuarios() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/usuario").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void getUsuario() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/usuario/1").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }
}
