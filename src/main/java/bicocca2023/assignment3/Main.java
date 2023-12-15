package bicocca2023.assignment3;

import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        initExceptionHandler((e) -> {
            System.out.println("Server init failed.");
            System.exit(100);
        });
        port(8000);
        init();

        System.out.println("Server is running on port 8000...");

        get("/hello", (Request req, Response res) -> "Hello, World!");
    }
}