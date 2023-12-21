package bicocca2023.assignment3;

import bicocca2023.assignment3.controller.LandmarkController;
import bicocca2023.assignment3.controller.UserController;
import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.VipPlanUser;
import bicocca2023.assignment3.repository.UserRepository;
import bicocca2023.assignment3.util.PersistenceManager;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManager;
import spark.Spark;

import java.util.List;

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

        Spark.get("/users", userController::getAllUsers);
        Spark.get("/users/vip/", userController::getVipUsers);
        Spark.get("/users/basic/", userController::getBasicUsers);
        Spark.get("/users/:id", userController::getUserById);
        Spark.post("/users", userController::createUser);
        Spark.put("/users/:id", userController::updateUser);
        Spark.put("/users/:id/upgrade", userController::upgradeUserToVip);
        Spark.put("/users/:id/demote", userController::demoteUserToBasic);
        Spark.delete("/users/:id", userController::deleteUser);

        Spark.get("/landmarks", landmarkController::getAllLandmarks);
        Spark.post("/landmarks", landmarkController::createLandmark);

        Spark.awaitStop();
    }
}