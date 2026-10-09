package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

import org.testng.annotations.Test;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class StartServer {

	private static final int PORT = 8080;
	private static final String WEB_ROOT = "src/test/resources/app";
	private static final String INDEX_FILE = "peopleflow-hrms.html";

	public static void startServer() throws Exception {

		HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

		server.createContext("/", new StaticFileHandler());

		server.setExecutor(null);

		server.start();

		System.out.println("HRMS local server running at: http://localhost:" + PORT);

		System.out.println("Serving files from: " + new File(WEB_ROOT).getAbsolutePath());
	}

	static class StaticFileHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws java.io.IOException {
			String requestPath = exchange.getRequestURI().getPath();

			// Map "/" to the index file
			if (requestPath.equals("/")) {
				requestPath = "/" + INDEX_FILE;
			}

			File file = new File(WEB_ROOT, requestPath).getCanonicalFile();

			// Basic safety check: don't allow escaping the web root
			File root = new File(WEB_ROOT).getCanonicalFile();
			if (!file.getPath().startsWith(root.getPath()) || !file.exists() || file.isDirectory()) {
				String notFound = "404 Not Found: " + requestPath;
				exchange.sendResponseHeaders(404, notFound.length());
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(notFound.getBytes());
				}
				return;
			}

			String contentType = guessContentType(file.getName());
			byte[] fileBytes;
			try (FileInputStream fis = new FileInputStream(file)) {
				fileBytes = fis.readAllBytes();
			}

			exchange.getResponseHeaders().set("Content-Type", contentType);
			exchange.sendResponseHeaders(200, fileBytes.length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(fileBytes);
			}
		}

		private String guessContentType(String fileName) {
			try {
				String type = Files.probeContentType(new File(fileName).toPath());
				if (type != null)
					return type;
			} catch (Exception ignored) {
			}

			if (fileName.endsWith(".html"))
				return "text/html";
			if (fileName.endsWith(".css"))
				return "text/css";
			if (fileName.endsWith(".js"))
				return "application/javascript";
			if (fileName.endsWith(".json"))
				return "application/json";
			if (fileName.endsWith(".png"))
				return "image/png";
			if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
				return "image/jpeg";
			if (fileName.endsWith(".svg"))
				return "image/svg+xml";
			return "application/octet-stream";
		}
	}
}