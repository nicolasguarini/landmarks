package bicocca2023.assignment3.repository;

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

    public User findByUsername(String username) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            List<User> users = entityManager
                    .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultList();

            if (users.isEmpty()){
                return null;
            } else {
                return users.get(0);
            }
        }
    }

    public List<User> findPopularUsers() {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery(
                            "SELECT u FROM User u " +
                                    "WHERE SIZE(u.landmarks) >= 2 " +
                                    "ORDER BY SIZE(u.followers) DESC", User.class)
                    .setMaxResults(5)
                    .getResultList();
        }
    }

    public User save(User user) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            try{
                entityManager.getTransaction().begin();
                if (user.getId() == null) {
                    entityManager.persist(user);
                } else {
                    user = entityManager.merge(user);
                }
                entityManager.getTransaction().commit();
                return user;
            }catch(Exception e){
                System.err.println("Error: " + e.getMessage());

                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }

                return null;
            }
        }
    }

    public void delete(UUID id) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            try{
                entityManager.getTransaction().begin();
                User user = entityManager.find(User.class, id);
                if (user != null) {
                    entityManager.remove(user);
                }
                entityManager.getTransaction().commit();
            }catch(Exception e){
                System.err.println("Error: " + e.getMessage());

                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
            }
        }
    }

    public User update(User user) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            try{
                entityManager.getTransaction().begin();

                if (user.getId() != null) {
                    user = entityManager.merge(user);
                }

                entityManager.getTransaction().commit();
                return user;
            }catch(Exception e){
                System.err.println("Error: " + e.getMessage());

                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }

                return null;
            }
        }
    }
}
