package br.com.gabrielarsenio.loja.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Gabriel Arsenio 24/03/2017.
 */
@Path("produtos")
public class ProdutoController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listar() {
        return "OK";
    }
}
