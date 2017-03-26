package br.com.gabrielarsenio.loja.resources;

import br.com.gabrielarsenio.loja.model.Estoque;

import javax.ws.rs.Path;

/**
 * Gabriel Arsenio 25/03/2017.
 */
@Path("estoque")
public class EstoqueController extends Controller {
    public EstoqueController() {
        super(Estoque.class);
    }
}
