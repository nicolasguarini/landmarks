package bicocca2023.assignment3.controller;

import bicocca2023.assignment3.exception.LandmarksLimitException;
import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.model.user.BasicPlanUser;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.VipPlanUser;
import bicocca2023.assignment3.service.LandmarkService;
import bicocca2023.assignment3.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.PersistenceException;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;

public class LandmarkController {
    private final LandmarkService landmarkService = new LandmarkService();
    private final UserService userService = new UserService();

    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public String createLandmark(Request request, Response response) {
        response.type("application/json");

        try{
            String name = request.queryMap("name").value();
            UUID userId = UUID.fromString(request.queryMap("userid").value());
            User user = userService.getUserById(userId);

            if(name == null){
                throw new IllegalArgumentException("No name provided");
            } else if(user == null){
                throw new IllegalArgumentException("User ID doesn't exist!");
            }

            Landmark landmark = new Landmark();;
            landmark.setName(name);
            landmark.setUser(userService.getUserById(userId));
            user.addLandmark(landmark);

            Landmark createdLandmark= landmarkService.createLandmark(landmark);
            if (createdLandmark != null) {
                response.status(201);
                return gson.toJson(createdLandmark);
            } else {
                response.status(400);
                return "Error creating user";
            }
        } catch (PersistenceException e){
            response.status(500);
            return "Error creating user" + e;
        } catch (LandmarksLimitException e) {
            response.status(400);
            return "User have reached landmarks limit";
        }
    }

    public String getAllLandmarks(Request request, Response response) {
        response.type("application/json");

        try{
            response.status(200);
            List<Landmark> landmarks = landmarkService.getAllLandmarks();
            return gson.toJson(landmarks);
        }catch(Exception e){
            response.status(500);
            e.printStackTrace();
            return "Error";
        }
    }
}
