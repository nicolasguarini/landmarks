package bicocca2023.assignment3;

import bicocca2023.assignment3.controller.LandmarkController;
import bicocca2023.assignment3.controller.UserController;
import bicocca2023.assignment3.util.PersistenceManager;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        PersistenceManager.initialize();
        UserController userController = new UserController();
        LandmarkController landmarkController = new LandmarkController();

        Spark.initExceptionHandler((e) -> {
            System.out.println("Server init failed.");
            System.exit(100);
        });

        Spark.port(8000);
        Spark.init();
        System.out.println("Server is running on port 8000...");

        // DEFINED PATHS FOR USERS API
        Routes.establishUserRoutes(userController);
        Routes.establishLandmarkRoutes(landmarkController);

        Spark.awaitStop();
    }
}