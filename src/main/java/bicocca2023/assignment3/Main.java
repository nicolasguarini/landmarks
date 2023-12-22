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

        Spark.path("/api", () -> {
            Spark.path("/users", () -> {
                // --------------- CREATE USER  ------------------------
                Spark.post("", userController::createUser);
                // ------------- GET USERS BY PLAN ---------------------
                Spark.get("/vip", userController::getVipUsers);
                Spark.get("/basic", userController::getBasicUsers);
                // ----------- GET USERS IN GENERAL OR BY ID------------
                Spark.get("", userController::getAllUsers);
                Spark.get("/:id", userController::getUserById);
                // ------------------ UPDATE USER ----------------------
                Spark.put("/:id", userController::updateUser);
                // ------------------ UPGRADE USER ----------------------
                Spark.put("/:id/upgrade", userController::upgradeUserToVip);
                // ------------------ DEMOTE USER ----------------------
                Spark.put("/:id/demote", userController::demoteUserToBasic);
                // ------------------ DELETE USER ----------------------
                Spark.delete("/:id", userController::deleteUser);
                Spark.post("/follow", userController::followUser);

            });

            // --------- CREATE RELATION USER FOLLOW ANOTHER USER -------
            // --------- DELETE RELATION USER FOLLOW ANOTHER USER -------
            // Spark.delete("/:followingId/follow/:followerId", userFollowerController::followUser);

            Spark.path("/landmarks", () -> {
                // --------- GET ALL LANDMARKS -----------
                Spark.get("", landmarkController::getAllLandmarks);
                // --------- CREATE LANDMARK -----------
                Spark.post("", landmarkController::createLandmark);
                // --------- CREATE LANDMARK BY ID -----------
                Spark.get("/:id", landmarkController::getLandmarkById);
                // -------    DELETE LANDMARK -----------
                Spark.delete("/:id", landmarkController::deleteLandmark);
                // -------    UPDATE LANDMARK -----------
                //Spark.put("/:id", landmarkController::updateLandMark);

            });

            Spark.awaitStop();
        });
    }
}