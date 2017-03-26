package br.com.gabrielarsenio.loja.resources;

import br.com.gabrielarsenio.loja.dao.DAO;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public abstract class Controller {

    private final Class entidade;
    private final String path;

    public Controller(Class<?> entidade) {
        this.entidade = entidade;
        this.path = this.getClass().getAnnotation(Path.class).value(); //Valor da anotação @Path que herdou desse
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvar(String conteudo) {
        Object registro = toObject(conteudo);
        Object registroSalvo = new DAO(entidade).salvar(registro);
        Integer codigo = 0;

        // Invoca o getCodigo da minha entidade pra devolver o código pro front
        try {
            Method getCodigo = registroSalvo.getClass().getMethod("getCodigo");
            codigo = (Integer) getCodigo.invoke(registroSalvo);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        URI uri = URI.create("/" + path + "/" + codigo);
        return Response.created(uri).build();
    }

    @Path("{codigo}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response alteraProduto(@PathParam("codigo") long codigo, String conteudo) {
        Object registro = toObject(conteudo);
        new DAO(entidade).salvar(registro);
        return Response.ok().build();
    }

    @Path("{codigo}")
    @DELETE
    public Response excluir(@PathParam("codigo") Integer codigo) {
        new DAO(entidade).excluir(codigo);
        return Response.ok().build();
    }

    @Path("{codigo}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarPorId(@PathParam("codigo") Integer codigo) {
        Object registro = new DAO(entidade).buscarPorId(codigo);
        return toJson(registro);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarTodos(@QueryParam("inicio") Integer inicio, @QueryParam("quantidade") Integer quantidade) {
        List<?> registros = new DAO(entidade).buscarTodos(inicio, quantidade);
        return toJson(registros);
    }

    private Object toObject(String jsonObj) {
        return new Gson().fromJson(jsonObj, entidade);
    }

    private String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
