package bicocca2023.assignment3.service;

import bicocca2023.assignment3.model.User;
import bicocca2023.assignment3.repository.UsersRepository;

import java.util.List;

public class UserService {
    private final UsersRepository usersRepository = new UsersRepository();

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public User createUser(User user) {
        return usersRepository.save(user);
    }

    public User updateUser(User user, Long id) {
        User existingUser = usersRepository.findById(id);

        if (existingUser != null) {
            usersRepository.save(user);
        } else {
            System.err.println("ERR: user not FOUND!");
            return null;
        }
        return usersRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        usersRepository.delete(id);
    }
}
