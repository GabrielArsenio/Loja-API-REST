package br.com.gabrielarsenio.loja;

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
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

/**
 * Gabriel Arsenio 26/03/2017.
 */
public class ClientAutenticacao {

    private static HttpServer server;

    @BeforeAll
    public static void iniciaAplicacao() {
        server = Servidor.startHttpServer();
    }

    @AfterAll
    public static void encerraAplicacao() {
        server.stop();
    }

    //@Test
    public void testaNaoAutorizado() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/produtos").request().get();

        Assertions.assertEquals(Response.Status.UNAUTHORIZED, res.getStatusInfo());
    }

    @Test
    public void testaAutorizado() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Usuario usr = new Usuario();
        usr.setUsuario("JUnit");
        usr.setSenha("tinUJ");

        session.saveOrUpdate(usr);
        transaction.commit();
        session.close();

        Form form = new Form();
        form.param("usuario", usr.getUsuario());
        form.param("senha", usr.getSenha());

        String token = target.path("/autenticar").request().post(Entity.form(form), String.class);
        Response res = target.path("/produtos").request().header("x-access-token", token).get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());

    }
}