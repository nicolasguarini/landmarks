package bicocca2023.assignment3.controller;

import bicocca2023.assignment3.model.User;
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
            System.out.println("Number of users: " + users.size());
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



}
