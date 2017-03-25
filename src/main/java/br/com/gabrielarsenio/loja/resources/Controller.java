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
public abstract class Controller<T> {

    private Class classe;
    private String path;

    public Controller() {
    }

    public Controller(Class classe) {
        this.classe = classe;
        this.path = this.getClass().getAnnotation(Path.class).value(); //Pega o valor da Path que herdou desse
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvar(String conteudo) {
        T entidade = toEntity(conteudo);
        Object entidadeSalva = new DAO(classe).salvar(entidade);
        Integer codigo = 0;

        // Invoca o getCodigo da minha entidade pra devolver o código pro front
        try {
            Method getCodigo = entidadeSalva.getClass().getMethod("getCodigo");
            codigo = (Integer) getCodigo.invoke(entidadeSalva);
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
        T entidade = toEntity(conteudo);
        new DAO(classe).salvar(entidade);
        return Response.ok().build();
    }

    @Path("{codigo}")
    @DELETE
    public Response excluir(@PathParam("codigo") Integer codigo) {
        new DAO(classe).excluir(codigo);
        return Response.ok().build();
    }

    @Path("{codigo}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarPorId(@PathParam("codigo") Integer codigo) {
        T entidade = (T) new DAO(classe).buscarPorId(codigo);
        return toJson(entidade);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarTodos(@QueryParam("inicio") Integer inicio, @QueryParam("quantidade") Integer quantidade) {
        List<T> entidades = (List<T>) new DAO(classe).buscarTodos(inicio, quantidade);
        return toJson(entidades);
    }

    private T toEntity(String jsonObj) {
        return (T) new Gson().fromJson(jsonObj, classe);
    }

    private String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
