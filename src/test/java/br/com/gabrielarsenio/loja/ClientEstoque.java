package br.com.gabrielarsenio.loja;

import br.com.gabrielarsenio.loja.main.Servidor;
import br.com.gabrielarsenio.loja.model.Estoque;
import org.glassfish.grizzly.http.server.HttpServer;
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
public class ClientEstoque {
    
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
    public void postEstoques() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Estoque e = new Estoque();
        e.setCodigo(1);

        Response res = target.path("/estoque").request().post(Entity.json(e.toJson()));

        Assertions.assertEquals(Response.Status.CREATED, res.getStatusInfo());
    }

    @Test
    public void putEstoques() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Estoque p = new Estoque();
        p.setCodigo(1);

        Response res = target.path("/estoque/1").request().put(Entity.json(p.toJson()));

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void deleteEstoques() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/estoque/1").request().delete();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void getEstoques() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/estoque").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void getEstoque() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/estoque/1").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }
}
