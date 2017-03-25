package br.com.gabrielarsenio.loja.main;

import br.com.gabrielarsenio.loja.dao.ProdutoDAO;
import br.com.gabrielarsenio.loja.model.Produto;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;
import java.util.List;

/**
 * Gabriel Arsenio 24/03/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        HttpServer server = Servidor.startHttpServer();

        System.out.println("Servidor ON");
        System.in.read();
        server.stop();

//        Produto p = new Produto();
//        p.setCodigo(2);
//        p.setNome("EDITADO");
//        List<Produto> lista = new ProdutoDAO().buscarTodos();

        System.out.println("FIM");
        System.exit(0);
    }
}