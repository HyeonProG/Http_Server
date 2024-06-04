package ch01;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer2 {

	public static void main(String[] args) {

		try {
			HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
			httpServer.createContext("/test2", new MyTestHandler());
			httpServer.start();
			System.out.println("server started");
		} catch (IOException e) {
			e.printStackTrace();
		}
	

	} // end of main

	static class MyTestHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String method = exchange.getRequestMethod();
			System.out.println("method : " + method);

			if ("GET".equalsIgnoreCase(method)) {
				handleGetRequest(exchange);
			} else if ("POST".equalsIgnoreCase(method)) {
				handlePostRequest(exchange);
			} else {
				String response = "Unsupported Method : " + method;
				exchange.sendResponseHeaders(405, response.length());
//				OutputStream os = exchange.getResponseBody();
//				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
				bw.write(response);
				bw.flush();
				bw.close();
			}
		}

		private void handleGetRequest(HttpExchange exchange) throws IOException {
			String response = """
					<!DOCTYPE html>
					<html lang=ko>
						<head></head>
						<body>
							<h1 style="background-color:yellow"> Hello path by /test2 </h1>
						</body>
					</html>
					""";

			exchange.sendResponseHeaders(200, response.length());
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
			bw.write(response);
			bw.close();
		}

		private void handlePostRequest(HttpExchange exchange) throws IOException {
			String response = """
					<!DOCTYPE html>
					<html lang=ko>
						<head></head>
						<body>
							<h1 style="background-color:yellow"> Hello path by /test2 </h1>
						</body>
					</html>
					""";
			exchange.sendResponseHeaders(200, response.length());
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()));
			bw.write(response);
			bw.flush();
			bw.close();
		}

	}

} // end of class
