package bicocca2023.assignment3.service;

import bicocca2023.assignment3.model.user.BasicPlanUser;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.VipPlanUser;
import bicocca2023.assignment3.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository usersRepository = new UserRepository();

    public List<User> getAllUsers() { return usersRepository.findAll(); }

    public List<VipPlanUser> getVipUsers() { return usersRepository.findAllVips(); }

    public List<BasicPlanUser> getBasicUsers() { return usersRepository.findAllBasics(); }

    public User getUserById(UUID id) { return usersRepository.findById(id); }

    public User createUser(User user) { return usersRepository.save(user); }

    public User updateUser(User user, UUID id) {
        User existingUser = usersRepository.findById(id);

        if (existingUser != null) {
            usersRepository.save(user);
        } else {
            System.err.println("ERR: user not FOUND!");
            return null;
        }
        return usersRepository.save(existingUser);
    }

    public void deleteUser(UUID id) { usersRepository.delete(id); }

}
