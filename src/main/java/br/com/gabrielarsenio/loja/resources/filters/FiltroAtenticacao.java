package br.com.gabrielarsenio.loja.resources.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Gabriel Arsenio 26/03/2017.
 */

@Provider
public class FiltroAtenticacao implements ContainerRequestFilter {

    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        Boolean isDesenv = true; // eu sei
        String method = containerRequestContext.getMethod();
        String path = containerRequestContext.getUriInfo().getPath();
        MultivaluedMap headers = containerRequestContext.getHeaders();
        String headerAutorizacao = (String) headers.getFirst("x-access-token");

        if (method.equals("OPTIONS") || path.equals("/autenticar") || isDesenv) {
            return;
        }

        try {
            if (headerAutorizacao == null) {
                throw new Exception("Não autorizado.");
            }
        } catch (Exception ex) {
            System.out.println("NAO AUTORIZADO");
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

    }
}
