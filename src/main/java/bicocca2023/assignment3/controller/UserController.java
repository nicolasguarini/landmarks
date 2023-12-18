package bicocca2023.assignment3.controller;

import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.service.UserService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.List;

public class UserController {
    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    public String getAllUsers(Request request, Response response){
        response.type("application/json");

        try{
            List<User> users = userService.getAllUsers();
            for(User u : users){
                System.out.println(u);
            }
            return gson.toJson(users);
        }catch(Exception e){
            e.printStackTrace();
            return "Error in getAllUsers:" + e;
        }
    }

    public String getUserById(Request request, Response response) {
        response.type("application/json");

        try {
            Long userId = Long.parseLong(request.params(":id"));
            User user = userService.getUserById(userId);

            if (user != null) {
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

        try {
            User newUser = gson.fromJson(request.body(), User.class);
            User createdUser = userService.createUser(newUser);

            if (createdUser != null) {
                response.status(201); // Created
                return gson.toJson(createdUser);
            } else {
                response.status(400); // Bad Request
                return "Error creating user";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.status(500);
            return "Error in createUser: " + e;
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

    public String deleteUser(Request request, Response response) {
        response.type("application/json");

        try {
            Long userId = Long.parseLong(request.params(":id"));
            userService.deleteUser(userId);
            response.status(204); // No Content
            return "";
        } catch (NumberFormatException e) {
            response.status(400);
            return "Invalid user ID format";
        } catch (Exception e) {
            e.printStackTrace();
            response.status(500);
            return "Error in deleteUser: " + e;
        }
    }
*/
}

