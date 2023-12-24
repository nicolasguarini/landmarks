package bicocca2023.assignment3.service;

import bicocca2023.assignment3.exception.LandmarksLimitException;
import bicocca2023.assignment3.model.Landmark;
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

    public void deleteUser(UUID id) { userRepository.delete(id); }

    public User updateUser(User user) { return userRepository.update(user); }

    public void followUser(User user, User userToFollow) {
        user.followUser(userToFollow);
        this.updateUser(user);
    }

    public void unfollowUser(User user, User userToUnfollow) {
        user.unfollowUser(userToUnfollow);
        this.updateUser(user);
    }

    public User upgradeUserToVip(User user) {
        LandmarkService landmarkService = new LandmarkService();

        if(user instanceof BasicPlanUser){
            VipPlanUser vipUser = new VipPlanUser();
            vipUser.setUsername(user.getUsername());
            List<Landmark> landmarks = user.getLandmarks();

            deleteUser(user.getId());
            createUser(vipUser);

            for(Landmark l : landmarks){
                Landmark newLandmark = new Landmark();
                newLandmark.setUser(vipUser);
                newLandmark.setName(l.getName());
                newLandmark.setCoordinate(l.getCoordinate());

                try{
                    vipUser.addLandmark(newLandmark);
                    landmarkService.createLandmark(newLandmark);
                }catch(LandmarksLimitException e) {
                    break;
                }
            }

            return vipUser;
        }

        return user;
    }

    public User demoteUserToBasic(User user) {
        LandmarkService landmarkService = new LandmarkService();

        if (user instanceof VipPlanUser) {
            BasicPlanUser basicUser = new BasicPlanUser();
            basicUser.setUsername(user.getUsername());
            List<Landmark> landmarks = user.getLandmarks();

            deleteUser(user.getId());
            createUser(basicUser);

            for (Landmark l : landmarks) {
                Landmark newLandmark = new Landmark();
                newLandmark.setUser(basicUser);
                newLandmark.setName(l.getName());
                newLandmark.setCoordinate(l.getCoordinate());

                try {
                    basicUser.addLandmark(newLandmark);
                    landmarkService.createLandmark(newLandmark);
                } catch (LandmarksLimitException e) {
                    break;
                }
            }

            return basicUser;
        }

        return user;
    }

}
