package br.com.gabrielarsenio.loja.resources;

import br.com.gabrielarsenio.loja.model.CategoriaProduto;

import javax.ws.rs.Path;

/**
 * Gabriel Arsenio 25/03/2017.
 */
@Path("categoria-produto")
public class CategoriaProdutoController extends Controller<CategoriaProduto> {

    public CategoriaProdutoController() {
        super(CategoriaProduto.class);
    }
}

