package bicocca2023.assignment3.service;

import bicocca2023.assignment3.model.user.BasicPlanUser;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.VipPlanUser;
import bicocca2023.assignment3.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public List<VipPlanUser> getVipUsers() { return userRepository.findAllVips(); }

    public List<BasicPlanUser> getBasicUsers() { return userRepository.findAllBasics(); }

    public User getUserById(UUID id) { return userRepository.findById(id); }

    public User createUser(User user) { return userRepository.save(user); }

    public User updateUser(User user) { return userRepository.update(user); }

    public void deleteUser(UUID id) { userRepository.delete(id); }

}
