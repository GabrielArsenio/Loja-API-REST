package br.com.gabrielarsenio.loja.resources;

import br.com.gabrielarsenio.loja.dao.UsuarioDAO;
import br.com.gabrielarsenio.loja.model.Usuario;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Gabriel Arsenio 26/03/2017.
 */
@Path("autenticar")
public class AutenticacaoController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response autenticar(@FormParam("usuario") String usuario, @FormParam("senha") String senha) {
        Usuario usr = new UsuarioDAO().buscarUsuario(usuario, senha);

        try {
            if (usr == null) {
                throw new Exception("NAO AUTORIZADO");
            }

            String token = "isso é um token, pode entrar";

            return Response.ok(token).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
