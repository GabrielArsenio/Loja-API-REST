package br.com.gabrielarsenio.loja;

import br.com.gabrielarsenio.loja.main.Servidor;
import br.com.gabrielarsenio.loja.model.CategoriaProduto;
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
 * Gabriel Arsenio 25/03/2017.
 */
public class ClientCategoriaProduto {

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
    public void postCategoriaProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        CategoriaProduto cp = new CategoriaProduto();
        cp.setCodigo(1);
        cp.setNome("[TESTE JUnit] CategoriaProduto INSERIDO");

        Response res = target.path("/categoria-produto").request().post(Entity.json(cp.toJson()));

        Assertions.assertEquals(Response.Status.CREATED, res.getStatusInfo());
    }

    @Test
    public void putCategoriaProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        CategoriaProduto cp = new CategoriaProduto();
        cp.setCodigo(1);
        cp.setNome("[TESTE JUnit] CategoriaProduto ALTERADO");

        Response res = target.path("/categoria-produto/1").request().put(Entity.json(cp.toJson()));

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void deleteCategoriaProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/categoria-produto/1").request().delete();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void getCategoriasProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/categoria-produto").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }

    @Test
    public void getCategoriaProduto() {
        WebTarget target = ClientFactory.newClient().target(Servidor.BASE_URI);

        Response res = target.path("/categoria-produto/1").request().get();

        Assertions.assertEquals(Response.Status.OK, res.getStatusInfo());
    }
}
