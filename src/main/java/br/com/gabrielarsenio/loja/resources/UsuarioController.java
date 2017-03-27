package br.com.gabrielarsenio.loja.resources;

import br.com.gabrielarsenio.loja.model.Usuario;

import javax.ws.rs.Path;

/**
 * Gabriel Arsenio 26/03/2017.
 */
@Path("usuario")
public class UsuarioController extends Controller {
    public UsuarioController() {
        super(Usuario.class);
    }
}
