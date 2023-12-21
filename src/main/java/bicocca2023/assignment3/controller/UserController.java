package bicocca2023.assignment3.controller;

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
            e.printStackTrace();
            return "Error in getAllUsers:" + e;
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
            e.printStackTrace();
            return "Error:" + e.getMessage();
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
            e.printStackTrace();
            return "Error:" + e.getMessage();
        }
    }

    public String getUserById(Request request, Response response) {
        response.type("application/json");

        try {
            UUID userId = UUID.fromString(request.params(":id"));
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

        try {
            String username = request.queryMap("username").value();
            String userType = request.queryMap("type").value();

            if (username == null) {
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
        } catch (PersistenceException e) {
            response.status(500);
            return "Error creating user";
        }
    }

    public String deleteUser(Request request, Response response) {
        try {
            UUID userId = UUID.fromString(request.params(":id"));
            userService.deleteUser(userId);
            response.status(200);
            return gson.toJson("User [:id ->" + request.params(":id") + "] successfully deleted");
        } catch (NumberFormatException e) {
            response.status(400);
            return "Invalid user ID format";
        } catch (Exception e) {
            response.status(500);
            return "Error in deleteUser: " + e.getMessage();
        }
    }

    public String updateUser(Request request, Response response) {
        response.type("application/json");

        try {
            UUID userId = UUID.fromString(request.params(":id"));
            User existingUser = userService.getUserById(userId);

            if (existingUser != null) {
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
                    return "Error updating user";
                }
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

    public String upgradeUserToVip(Request request, Response response) {
        response.type("application/json");

        try {
            UUID userId = UUID.fromString(request.params(":id"));
            User existingUser = userService.getUserById(userId);

            if (existingUser != null) {
                User upgradedUser = userService.upgradeUserToVip(existingUser);

                response.status(200);
                return gson.toJson(upgradedUser);
            } else {
                response.status(404);
                return "User not found or not eligible for upgrade";
            }
        } catch (NumberFormatException e) {
            response.status(400);
            return "Invalid user ID format";
        } catch (Exception e) {
            e.printStackTrace();
            response.status(500);
            return "Error in upgradeUserToVip: " + e;
        }
    }

    public String demoteUserToBasic(Request request, Response response) {
        response.type("application/json");

        try {
            UUID userId = UUID.fromString(request.params(":id"));
            User existingUser = userService.getUserById(userId);

            if (existingUser != null) {
                User demotedUser = userService.demoteUserToBasic(existingUser);

                response.status(200);
                return gson.toJson(demotedUser);
            } else {
                response.status(404);
                return "User not found or not eligible for upgrade";
            }
        } catch (NumberFormatException e) {
            response.status(400);
            return "Invalid user ID format";
        } catch (Exception e) {
            e.printStackTrace();
            response.status(500);
            return "Error in upgradeUserToVip: " + e;
        }
    }

    /*
        public String followUser(Request request, Response response) {
            response.type("application/json");

            try {
                UUID followerId = UUID.fromString(request.params(":followerId"));
                UUID followingId = UUID.fromString(request.params(":followingId"));

                User follower = userService.getUserById(followerId);
                User following = userService.getUserById(followingId);

                if (follower != null && following != null) {
                    follower.follow(following);
                    userService.updateUser(follower);

                    response.status(200);
                    return gson.toJson("User " + followerId + " is now following user " + followingId);
                } else {
                    response.status(404);
                    return "User not found";
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return "Invalid user ID format";
            } catch (Exception e) {
                response.status(500);
                return "Error in followUser: " + e;
            }
        }
    */
}
