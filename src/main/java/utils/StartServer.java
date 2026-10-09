
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class StartServer {

    private static final int PORT = 8080;

    private static final String WEB_ROOT =
            "C:\\Projects\\HRMS-Automation\\src\\test\\resources\\app";

    private static final String INDEX_FILE = "peopleflow-hrms.html";

    public static void main(String[] args) throws Exception {
        startServer();
    }

    public static void startServer() throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(PORT), 0);

        server.createContext("/", new StaticFileHandler());

        server.setExecutor(null);

        server.start();

        System.out.println(
                "HRMS local server running at: http://localhost:" + PORT);

        System.out.println(
                "Serving files from: "
                        + new File(WEB_ROOT).getAbsolutePath());
    }

    static class StaticFileHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange)
                throws java.io.IOException {

            String requestPath = exchange.getRequestURI().getPath();

            // Map the root URL to the HTML file
            if (requestPath.equals("/")) {
                requestPath = "/" + INDEX_FILE;
            }

            File root = new File(WEB_ROOT).getCanonicalFile();

            File file = new File(
                    root, requestPath.substring(1)).getCanonicalFile();

            // Prevent access outside the web root
            if (!file.toPath().startsWith(root.toPath())
                    || !file.exists()
                    || file.isDirectory()) {

                byte[] message = (
                        "404 Not Found: " + requestPath)
                        .getBytes(java.nio.charset.StandardCharsets.UTF_8);

                exchange.sendResponseHeaders(404, message.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(message);
                }

                return;
            }

            byte[] fileBytes;

            try (FileInputStream fis = new FileInputStream(file)) {
                fileBytes = fis.readAllBytes();
            }

            exchange.getResponseHeaders().set(
                    "Content-Type", guessContentType(file.getName()));

            exchange.sendResponseHeaders(200, fileBytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(fileBytes);
            }
        }

        private String guessContentType(String fileName) {

            try {
                String type = Files.probeContentType(
                        new File(fileName).toPath());

                if (type != null) {
                    return type;
                }
            } catch (Exception ignored) {
            }

            if (fileName.endsWith(".html"))
                return "text/html; charset=UTF-8";

            if (fileName.endsWith(".css"))
                return "text/css; charset=UTF-8";

            if (fileName.endsWith(".js"))
                return "application/javascript; charset=UTF-8";

            if (fileName.endsWith(".json"))
                return "application/json; charset=UTF-8";

            if (fileName.endsWith(".png"))
                return "image/png";

            if (fileName.endsWith(".jpg")
                    || fileName.endsWith(".jpeg"))
                return "image/jpeg";

            if (fileName.endsWith(".svg"))
                return "image/svg+xml";

            return "application/octet-stream";
        }
    }
}
