package bicocca2023.assignment3.controller;

import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.model.user.BasicPlanUser;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.VipPlanUser;
import bicocca2023.assignment3.service.LandmarkService;
import bicocca2023.assignment3.service.UserService;
import com.google.gson.Gson;
import jakarta.persistence.PersistenceException;
import spark.Request;
import spark.Response;

import java.util.UUID;

public class LandmarkController {

    private final LandmarkService landmarkService = new LandmarkService();
    private final UserService userService = new UserService();

    private final Gson gson = new Gson();

    public String createLandmark(Request request, Response response) {
        response.type("application/json");

        try{
            String name = String.format(request.params(":name"));
            UUID userId = UUID.fromString(request.queryMap("id").value());

            if(userService.getUserById(userId) == null){
                throw new IllegalArgumentException("User ID doesn't exist!");
            }

            if(name == null){
                throw new IllegalArgumentException("No name provided");
            }

            Landmark landmark = new Landmark();;
            landmark.setName(name);
            landmark.setUser(userService.getUserById(userId));

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
        }
    }

}
