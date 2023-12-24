package bicocca2023.assignment3;

import bicocca2023.assignment3.controller.LandmarkController;
import bicocca2023.assignment3.controller.UserController;
import spark.Spark;

public class Routes {
    public static void establishUserRoutes(UserController userController){
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

                //  ------------------ FOLLOW / UNFOLLOW USER ----------------------
                Spark.post("/:id/follow/:idToFollow", userController::followUser);

                Spark.patch("/:id/unfollow/:idToUnfollow", userController::unfollowUser);

                Spark.get("/:id/followers", userController::getFollowersById);

                Spark.get("/:id/followings", userController::getFollowingsById);
            });
        });
    }

    public static void establishLandmarkRoutes(LandmarkController landmarkController) {
        Spark.path("/api", () -> {
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
        });
    }
}