package bicocca2023.assignment3;

import bicocca2023.assignment3.controller.LandmarkController;
import bicocca2023.assignment3.controller.UserController;
import spark.Spark;

public class Routes {
    public static void establishUserRoutes(UserController userController){
        Spark.path("/api/users", () -> {
            Spark.post("", userController::createUser);
            Spark.get("", userController::getAllUsers);
            Spark.get("/:id", userController::getUserById);
            Spark.put("/:id/update", userController::updateUser);
            Spark.delete("/:id", userController::deleteUser);

            Spark.get("/vip", userController::getVipUsers);
            Spark.get("/basic", userController::getBasicUsers);

            Spark.post("/:id/follow/:idToFollow", userController::followUser);
            Spark.post("/:id/unfollow/:idToUnfollow", userController::unfollowUser);
            Spark.get("/:id/followers", userController::getFollowersById);
            Spark.get("/:id/followings", userController::getFollowingsById);

            Spark.get("/:id/landmarks", userController::getUserLandmarks);
            Spark.get("/query/most-populars", userController::getPopularUsers);
        });
    }

    public static void establishLandmarkRoutes(LandmarkController landmarkController) {
        Spark.path("/api/landmarks", () -> {
            Spark.post("", landmarkController::createLandmark);
            Spark.get("", landmarkController::getAllLandmarks);
            Spark.get("/:id", landmarkController::getLandmarkById);
            Spark.put("/:id/update", landmarkController::updateLandMark);
            Spark.delete("/:id", landmarkController::deleteLandmark);
        });
    }
}