package bicocca2023.assignment3.controller;

import bicocca2023.assignment3.exception.LandmarksLimitException;
import bicocca2023.assignment3.model.Coordinate;
import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.model.user.User;
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

        try {
            String name = request.queryMap("name").value();
            UUID userId = UUID.fromString(request.queryMap("userid").value());
            Double latitude = request.queryMap("lat").doubleValue();
            Double longitude = request.queryMap("long").doubleValue();

            User user = userService.getUserById(userId);

            if (name == null || latitude == null || longitude == null) {
                throw new IllegalArgumentException("Name, latitude, or longitude not provided");
            } else if (user == null) {
                throw new IllegalArgumentException("User ID doesn't exist!");
            }

            Landmark landmark = new Landmark();
            landmark.setName(name);

            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(latitude);
            coordinate.setLongitude(longitude);

            landmark.setCoordinate(coordinate);

            landmark.setUser(userService.getUserById(userId));
            user.addLandmark(landmark);

            Landmark createdLandmark = landmarkService.createLandmark(landmark);
            if (createdLandmark != null) {
                response.status(201);
                return gson.toJson(createdLandmark);
            } else {
                response.status(400);
                return gson.toJson("Error creating landmark");
            }
        } catch (PersistenceException e) {
            response.status(500);
            return gson.toJson("Error creating landmark [Error: " + e + "]");
        } catch (LandmarksLimitException e) {
            response.status(400);
            return gson.toJson("User has reached landmarks limit");
        }
    }

    public String getAllLandmarks(Request request, Response response) {
        response.type("application/json");

        try {
            response.status(200);
            List<Landmark> landmarks = landmarkService.getAllLandmarks();
            System.out.println("Number of landmarks retrieved: " + landmarks.size());
            return gson.toJson(landmarks);
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error in getAllLandmarks:" + e.getMessage());
        }
    }

    public String deleteLandmark(Request request, Response response) {
        try {
            UUID landmarkId = UUID.fromString(request.params(":id"));
            landmarkService.deleteLandmark(landmarkId);
            response.status(200);
            return gson.toJson("Landmark [:id ->" + request.params(":id") + "] successfully deleted");

        } catch (Exception e) {
            response.status(500);
            return gson.toJson( "Error in deleteLandmark: " + e.getMessage());
        }
    }

    public String getLandmarkById(Request request, Response response) {
        response.type("application/json");
        try {
            UUID landmarkId = UUID.fromString(request.params(":id"));
            Landmark landmark = landmarkService.getLandmarkById(landmarkId);

            if (landmark != null) {
                response.status(200);
                return gson.toJson(landmark);
            } else {
                response.status(410);
                return "User not found";
            }
        } catch (NumberFormatException e) {
            response.status(400);
            return "Invalid user ID format";
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error in getUserById: " + e.getMessage());
        }
    }

    public String updateLandMark(Request request, Response response) {
        response.type("application/json");

        try{
            UUID landmarkId = UUID.fromString(request.params("id"));

            String name = request.queryMap("name").value();
            String description = request.queryMap("description").value();
            String lat = request.queryMap("lat").value();
            String lon = request.queryMap("lon").value();

            Landmark landmark = landmarkService.getLandmarkById(landmarkId);

            if (landmark != null) {
                if(name != null) landmark.setName(name);
                if(description != null) landmark.setDescription(description);
                if(lat != null) landmark.getCoordinate().setLatitude(Double.parseDouble(lat));
                if(lon != null) landmark.getCoordinate().setLongitude(Double.parseDouble(lon));

                response.status(200);
                return gson.toJson(landmarkService.updateLandmark(landmark));
            }else{
                response.status(410);
                return gson.toJson("Landmark does not exist");
            }
        }catch (NumberFormatException e){
            response.status(400);
            return gson.toJson("Invalid number format");
        }catch (Exception e) {
            response.status(500);
            return gson.toJson("Internal server error: " + e.getMessage());
        }
    }
}
