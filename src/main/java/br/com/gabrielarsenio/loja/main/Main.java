package br.com.gabrielarsenio.loja.main;

import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;

/**
 * Gabriel Arsenio 24/03/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        HttpServer server = Servidor.startHttpServer();

        System.out.println("Servidor ON");
        System.in.read();
        server.stop();

        System.out.println("FIM");
        System.exit(0);
    }
}