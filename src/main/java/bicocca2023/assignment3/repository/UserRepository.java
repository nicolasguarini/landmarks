package bicocca2023.assignment3.repository;

import bicocca2023.assignment3.model.User;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class UserRepository {
    public List<User> findAll() {
        EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager();

        try{
            return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    public User findById(Long id){
        EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            return entityManager.find(User.class, id);
        } finally {
            entityManager.close();
        }
    }

    public User save(User user) {
        EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (user.getId() == null) {
                entityManager.persist(user);
            } else {
                user = entityManager.merge(user);
            }
            entityManager.getTransaction().commit();
            return user;
        } finally {
            entityManager.close();
        }
    }

    public void delete(Long id) {
        EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, id);

            if (user != null) {
                entityManager.remove(user);
            }

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
