package br.com.gabrielarsenio.loja.main;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * Gabriel Arsenio 25/03/2017.
 */
public class Servidor {

    public static HttpServer startHttpServer() {
        URI uri = URI.create("http://localhost:3000/");
        ResourceConfig recursos = new ResourceConfig().packages("br.com.gabrielarsenio.loja");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, recursos);
        return server;
    }
}
