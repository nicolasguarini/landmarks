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
        Spark.path("/api", () -> {

            Spark.path("/users", () -> {
                Spark.post("", userController::createUser);
                Spark.get("", userController::getAllUsers);
                Spark.get("/vip", userController::getVipUsers);
                Spark.get("/basic", userController::getBasicUsers);
                Spark.get("/:id", userController::getUserById);
                Spark.put("/:id", userController::updateUser);
                Spark.put("/:id/upgrade", userController::upgradeUserToVip);
                Spark.put("/:id/demote", userController::demoteUserToBasic);
                Spark.delete("/:id", userController::deleteUser);
            });

            Spark.path("/landmarks", () -> {
                Spark.get("", landmarkController::getAllLandmarks);
                Spark.post("", landmarkController::createLandmark);
                Spark.get("/:id", landmarkController::getLandmarkById);
                Spark.delete("/:id", landmarkController::deleteLandmark);
            });

            Spark.awaitStop();
        });
    }
}