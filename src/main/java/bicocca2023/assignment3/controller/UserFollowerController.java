package bicocca2023.assignment3.controller;

import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.UserFollower;
import bicocca2023.assignment3.service.UserService;
import bicocca2023.assignment3.service.UserFollowerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import java.util.UUID;

public class UserFollowerController {

    UserService userService = new UserService();
    UserFollowerService userFollowerService = new UserFollowerService();
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


    public String followUser(Request request, Response response){
        response.type("application/json");

        try {
            UUID followerId = UUID.fromString(request.params(":followerId"));
            UUID followingId = UUID.fromString(request.params(":followingId"));

            User follower = userService.getUserById(followerId);
            User following = userService.getUserById(followingId);

            if (follower != null && following != null) {
                follower.follow(following);

                UserFollower newFollow = new UserFollower();

                newFollow.setFollower(follower);
                newFollow.setFollowing(following);

                UserFollower followToGson = userFollowerService.addFollow(newFollow);

                response.status(200);
                System.out.println("FOLLOWTOGSON: " + followToGson);
                return follower.getUsername() + " has started to follow " + following.getUsername();
            } else {
                response.status(404);
                return "UserFollower not found";
            }
        } catch (NumberFormatException e) {
            response.status(400);
            return "Invalid user ID format";
        } catch (Exception e) {
            response.status(500);
            System.err.println("Error in followUser: " + e.getMessage());
            e.printStackTrace();  // Print the full stack trace for debugging
            return "Error in followUser";
        }
    }
}
