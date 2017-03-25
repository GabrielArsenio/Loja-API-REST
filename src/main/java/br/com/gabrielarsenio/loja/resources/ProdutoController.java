package br.com.gabrielarsenio.loja.resources;

import br.com.gabrielarsenio.loja.model.Produto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Gabriel Arsenio 24/03/2017.
 */
@Path("produtos")
public class ProdutoController extends Controller<Produto> {

    public ProdutoController() {
        super(Produto.class);
    }
}
