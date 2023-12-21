package bicocca2023.assignment3.service;

import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.UserFollower;
import bicocca2023.assignment3.repository.UserFollowerRepository;

import java.util.UUID;

public class UserFollowerService {

    public UserFollower addFollow(UserFollower newFollow) { return UserFollowerRepository.create(newFollow); }

    //    public User updateUser(User user) { return userRepository.update(user); }
}
