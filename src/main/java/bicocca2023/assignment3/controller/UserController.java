package bicocca2023.assignment3.controller;

import bicocca2023.assignment3.model.user.BasicPlanUser;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.VipPlanUser;
import bicocca2023.assignment3.service.UserService;
import com.google.gson.Gson;
import jakarta.persistence.PersistenceException;
import spark.Request;
import spark.Response;

import java.util.List;

public class UserController {
    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    public String getAllUsers(Request request, Response response){
        response.type("application/json");

        try{
            response.status(200);
            List<User> users = userService.getAllUsers();
            return gson.toJson(users);
        }catch(Exception e){
            response.status(500);
            e.printStackTrace();
            return "Error in getAllUsers:" + e;
        }
    }

    public String getVipUsers(Request request, Response response) {
        response.type("application/json");

        try{
            response.status(200);
            List<VipPlanUser> users = userService.getVipUsers();
            return gson.toJson(users);
        }catch(Exception e){
            response.status(500);
            e.printStackTrace();
            return "Error:" + e.getMessage();
        }
    }

    public String getBasicUsers(Request request, Response response) {
        response.type("application/json");

        try{
            response.status(200);
            List<BasicPlanUser> users = userService.getBasicUsers();
            return gson.toJson(users);
        }catch(Exception e){
            response.status(500);
            e.printStackTrace();
            return "Error:" + e.getMessage();
        }
    }

    public String getUserById(Request request, Response response) {
        response.type("application/json");

        try {
            Long userId = Long.parseLong(request.params(":id"));
            User user = userService.getUserById(userId);

            if (user != null) {
                response.status(200);
                return gson.toJson(user);
            } else {
                response.status(404);
                return "User not found";
            }
        } catch (NumberFormatException e) {
            response.status(400);
            return "Invalid user ID format";
        } catch (Exception e) {
            e.printStackTrace();
            response.status(500);
            return "Error in getUserById: " + e;
        }
    }

    public String createUser(Request request, Response response) {
        response.type("application/json");

        try{
            String username = request.queryMap("username").value();
            String userType = request.queryMap("type").value();

            if (username == null){
                throw new IllegalArgumentException("No username provided");
            }

            User user;
            if (userType != null && userType.equalsIgnoreCase("vip")) {
                user = new VipPlanUser();
            } else {
                user = new BasicPlanUser();
            }

            user.setUsername(username.toLowerCase());

            User createdUser = userService.createUser(user);
            if (createdUser != null) {
                response.status(201);
                return gson.toJson(createdUser);
            } else {
                response.status(400);
                return "Error creating user";
            }
        } catch (PersistenceException e){
            response.status(500);
            return "Error creating user";
        }
    }

    public String deleteUser(Request request, Response response) {
        try {
            Long userId = Long.parseLong(request.params(":id"));
            userService.deleteUser(userId);
            response.status(200);
            return gson.toJson("User successfully deleted");
        } catch (NumberFormatException e) {
            response.status(400);
            return "Invalid user ID format";
        } catch (Exception e) {
            response.status(500);
            return "Error in deleteUser: " + e.getMessage();
        }
    }

    /*
    public String updateUser(Request request, Response response) {
        response.type("application/json");

        try {
            Long userId = Long.parseLong(request.params(":id"));
            String newUsername = gson.fromJson(request.body(), String.class);

            User updatedUser = userService.updateUser(newUsername, userId);

            if (updatedUser != null) {
                return gson.toJson(updatedUser);
            } else {
                response.status(404);
                return "User not found";
            }
        } catch (NumberFormatException e) {
            response.status(400);
            return "Invalid user ID format";
        } catch (Exception e) {
            e.printStackTrace();
            response.status(500);
            return "Error in updateUser: " + e;
        }
    }
     */
}

