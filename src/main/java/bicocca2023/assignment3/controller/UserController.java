package bicocca2023.assignment3.controller;

import bicocca2023.assignment3.exception.AlreadyFollowingException;
import bicocca2023.assignment3.exception.NotFollowingException;
import bicocca2023.assignment3.exception.UserAlreadyExistsException;
import bicocca2023.assignment3.exception.UserNotFoundException;
import bicocca2023.assignment3.model.user.BasicPlanUser;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.VipPlanUser;
import bicocca2023.assignment3.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.PersistenceException;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.UUID;

public class UserController {
    private final UserService userService = new UserService();
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public String getAllUsers(Request request, Response response) {
        response.type("application/json");

        try {
            response.status(200);
            List<User> users = userService.getAllUsers();
            return gson.toJson(users);
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error in getAllUsers:" + e.getMessage());
        }
    }

    public String getVipUsers(Request request, Response response) {
        response.type("application/json");

        try {
            response.status(200);
            List<VipPlanUser> users = userService.getVipUsers();
            return gson.toJson(users);
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error:" + e.getMessage());
        }
    }

    public String getBasicUsers(Request request, Response response) {
        response.type("application/json");

        try {
            response.status(200);
            List<BasicPlanUser> users = userService.getBasicUsers();
            return gson.toJson(users);
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error:" + e.getMessage());
        }
    }

    public String getUserById(Request request, Response response) {
        response.type("application/json");

        try {
            UUID userId = UUID.fromString(request.params(":id"));
            User user = userService.getUserById(userId);

            if (user == null) {
                throw new UserNotFoundException();
            }

            response.status(200);
            return gson.toJson(user);
        } catch (NumberFormatException e) {
            response.status(400);
            return gson.toJson("Invalid user ID format");
        } catch (UserNotFoundException e){
            response.status(410);
            return gson.toJson("User not found");
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error in getUserById: " + e.getMessage());
        }
    }

    public String createUser(Request request, Response response) {
        response.type("application/json");

        try {
            String username = request.queryMap("username").value();
            String userType = request.queryMap("type").value();

            if (username == null) {
                throw new IllegalArgumentException("No username provided");
            }

            if (userService.getUserByUsername(username) != null) {
                throw new UserAlreadyExistsException();
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
                return gson.toJson("Error creating user");
            }
        } catch (PersistenceException e) {
            response.status(500);
            return gson.toJson("Error creating user");
        } catch (UserAlreadyExistsException e) {
            response.status(400);
            return gson.toJson("User already exists.");
        }
    }

    public String deleteUser(Request request, Response response) {
        try {
            UUID userId = UUID.fromString(request.params(":id"));

            User existingUser = userService.getUserById(userId);

            if (existingUser == null) {
                throw new UserNotFoundException();
            }

            userService.deleteUser(userId);
            response.status(200);
            return gson.toJson("User [:id ->" + request.params(":id") + "] successfully deleted");
        } catch (NumberFormatException e) {
            response.status(400);
            return gson.toJson("Invalid user ID format");
        } catch (UserNotFoundException e){
            response.status(410);
            return gson.toJson("User not found");
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error in deleteUser: " + e.getMessage());
        }
    }

    public String updateUser(Request request, Response response) {
        response.type("application/json");

        try {
            UUID userId = UUID.fromString(request.params(":id"));
            User existingUser = userService.getUserById(userId);

            if (existingUser == null) {
                throw new UserNotFoundException();
            }

            String newUsername = request.queryMap("username").value();
            if (newUsername != null) {
                existingUser.setUsername(newUsername.toLowerCase());
            }

            User updatedUser = userService.updateUser(existingUser);

            if (updatedUser != null) {
                response.status(200);
                return gson.toJson(updatedUser);
            } else {
                response.status(500);
                return gson.toJson("Error updating user");
            }
        } catch (NumberFormatException e) {
            response.status(400);
            return gson.toJson("Invalid user ID format");
        } catch (UserNotFoundException e){
            response.status(410);
            return gson.toJson("User not found");
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error in updateUser: " + e.getMessage());
        }
    }

    public Object followUser(Request request, Response response) {
        response.type("application/json");
        try{
            UUID userId = UUID.fromString(request.params("id"));
            UUID userToFollowId = UUID.fromString(request.params("idToFollow"));

            User user = userService.getUserById(userId);
            User userToFollow = userService.getUserById(userToFollowId);

            if(user == null || userToFollow == null){
                throw new UserNotFoundException();
            }

            if (user.isFollowing(userToFollow)) {
                throw new AlreadyFollowingException();
            }

            userService.followUser(user, userToFollow);
            response.status(200);
            return gson.toJson("User " + user.getUsername() + " is now following user " + userToFollow.getUsername());
        } catch (UserNotFoundException e) {
            response.status(410);
            return gson.toJson("User not found.");
        } catch(AlreadyFollowingException e) {
            response.status(400);
            return gson.toJson("User is already following this user.");
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error in followUser: " + e.getMessage());
        }
    }

    public Object unfollowUser(Request request, Response response) {
        response.type("application/json");

        try {
            UUID userId = UUID.fromString(request.params("id"));
            UUID userToUnfollowId = UUID.fromString(request.params("idToUnfollow"));

            User user = userService.getUserById(userId);
            User userToUnfollow = userService.getUserById(userToUnfollowId);

            if (user == null || userToUnfollow == null) {
                throw new UserNotFoundException();
            }

            if (!user.isFollowing(userToUnfollow)) {
                throw new NotFollowingException();
            }

            userService.unfollowUser(user, userToUnfollow);
            response.status(200);
            return gson.toJson("User " + user.getUsername() + " has unfollowed user " + userToUnfollow.getUsername());
        } catch (UserNotFoundException e){
            response.status(410);
            return gson.toJson("User not found.");
        } catch (NotFollowingException e) {
            response.status(400);
            return gson.toJson("User is not following the other user");
        } catch (Exception e) {
            response.status(500);
            return gson.toJson("Error in unfollowUser: " + e.getMessage());
        }
    }

    public String getFollowersById(Request request, Response response) {
        response.type("application/json");

        UUID userId = UUID.fromString(request.params("id"));
        User user = userService.getUserById(userId);
        return gson.toJson(user.getFollowers());
    }

    public String getFollowingsById(Request request, Response response) {
        response.type("application/json");

        UUID userId = UUID.fromString(request.params("id"));
        User user = userService.getUserById(userId);
        return gson.toJson(user.getFollowings());
    }

    public String getUserLandmarks(Request request, Response response) {
        response.type("application/json");

        try {
            UUID userId = UUID.fromString(request.params("id"));
            User user = userService.getUserById(userId);

            if (user == null) {
                throw new UserNotFoundException();
            }

            response.status(200);
            return gson.toJson(user.getLandmarks());
        } catch (UserNotFoundException e){
            response.status(410);
            return gson.toJson("User not found.");
        } catch (Exception e){
            response.status(500);
            return gson.toJson("Error: " + e.getMessage());
        }
    }

    public String getPopularUsers(Request request, Response response) {
        response.type("application/json");
        return gson.toJson(userService.getPopularUsers());
    }
}


