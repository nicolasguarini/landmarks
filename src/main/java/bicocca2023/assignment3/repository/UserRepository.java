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
                delete(user.getId());
                VipPlanUser vipUser = new VipPlanUser(user.getId());
                vipUser.setUsername(user.getUsername());
                save(vipUser);
            }

            entityManager.getTransaction().commit();
            return user;
        }
    }

    public User demote(User user) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            if (user instanceof VipPlanUser) {
                delete(user.getId());
                BasicPlanUser basicUser = new BasicPlanUser(user.getId());
                basicUser.setUsername(user.getUsername());
                save(basicUser);
            }

            entityManager.getTransaction().commit();
            return user;
        }
    }
}