package com.github.renmat.zerod;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class ZeroD {

	public static void main(String[] args) throws Exception {
		startEchoServer();
	}
	
	private static void startEchoServer() throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8080), 0,"/",httpExchange -> {
        	var outContent = new StringBuilder();
        	var headers = httpExchange.getRequestHeaders();
        	for(var header:headers.entrySet()) {
        		outContent.append(header.getKey());
        	}
        	httpExchange.sendResponseHeaders(200, outContent.length());
            try (var outStream = httpExchange.getResponseBody()) {
            	outStream.write(outContent.toString().getBytes());
            }
        });
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
	}

}
