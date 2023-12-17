package bicocca2023.assignment3;

import bicocca2023.assignment3.controller.UserController;
import bicocca2023.assignment3.model.User;
import bicocca2023.assignment3.service.UserService;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersistenceManager.initialize();
        UserController userController = new UserController();

        Spark.initExceptionHandler((e) -> {
            System.out.println("Server init failed.");
            System.exit(100);
        });

        Spark.port(8000);
        Spark.init();
        System.out.println("Server is running on port 8000...");

        Spark.get("/users", userController::getAllUsers);
        Spark.get("/users/:id", userController::getUserById);

        Spark.awaitStop();
    }
}