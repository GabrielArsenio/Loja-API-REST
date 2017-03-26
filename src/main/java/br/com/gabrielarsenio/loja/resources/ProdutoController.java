package br.com.gabrielarsenio.loja.resources;

import br.com.gabrielarsenio.loja.model.Produto;

import javax.ws.rs.Path;

/**
 * Gabriel Arsenio 24/03/2017.
 */
@Path("produtos")
public class ProdutoController extends Controller {
    public ProdutoController() {
        super(Produto.class);
    }
}
