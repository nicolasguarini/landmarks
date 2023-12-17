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
            System.out.println("numero di utenti: " + users.size());
            return gson.toJson(userService.getAllUsers());
        }catch(Exception e){
            e.printStackTrace();
            return "crashed figa";
        }

    }
}
