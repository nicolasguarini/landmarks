package bicocca2023.assignment3.repository;

import bicocca2023.assignment3.exception.LandmarksLimitException;
import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.model.user.BasicPlanUser;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.VipPlanUser;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
public class UserRepository {
    public List<User> findAll() {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        }
    }

    public List<VipPlanUser> findAllVips() {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery("SELECT u FROM User u WHERE TYPE(u) = VipPlanUser ", VipPlanUser.class).getResultList();
        }
    }

    public List<BasicPlanUser> findAllBasics() {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery("SELECT u FROM BasicPlanUser u", BasicPlanUser.class).getResultList();
        }
    }

    public User findById(UUID id) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            return entityManager.find(User.class, id);
        }
    }

    public User save(User user) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            if (user.getId() == null) {
                entityManager.persist(user);
            } else {
                user = entityManager.merge(user);
            }
            entityManager.getTransaction().commit();
            return user;
        }
    }

    public void delete(UUID id) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);
            }
            entityManager.getTransaction().commit();
        }
    }

    public User update(User user) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            if (user.getId() != null) {
                // Existing user, merge it
                user = entityManager.merge(user);
            }

            entityManager.getTransaction().commit();
            return user;
        }
    }

    public User upgrade(User user) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            if (user instanceof BasicPlanUser) {
                List<Landmark> userLandmarks = user.getLandmarks();
                System.out.println("PIPPO " + userLandmarks.size());
                String username = user.getUsername();
                System.out.println("PIPPO " + username);

                VipPlanUser vipUser = new VipPlanUser();
                vipUser.setUsername(username);

                for(Landmark l : userLandmarks){
                    Landmark newLandmark = new Landmark();
                    newLandmark.setUser(vipUser);
                    newLandmark.setName(l.getName());
                    newLandmark.setCoordinate(l.getCoordinate());
                    vipUser.addLandmark(newLandmark);

                }

                System.out.println("PIPPO ecco quanti landmarks ci sono " + vipUser.getLandmarks().size()); // -> 1

                delete(user.getId());

                System.out.println("PIPPO ecco quanti landmarks ci sono dopo aver cancellato user " + vipUser.getLandmarks().size()); // -> 1
                save(vipUser);
                entityManager.getTransaction().commit();
                return vipUser;
            }

            entityManager.getTransaction().commit();
        } catch (LandmarksLimitException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public User demote(User user) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            if (user instanceof VipPlanUser) {
                delete(user.getId());
                BasicPlanUser basicUser = new BasicPlanUser();
                basicUser.setUsername(user.getUsername());
                save(basicUser);
                entityManager.getTransaction().commit();
                return basicUser;
            }

            entityManager.getTransaction().commit();
            return user;
        }
    }
}