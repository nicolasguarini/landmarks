package bicocca2023.assignment3.repository;

import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.model.user.UserFollower;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Transactional
public class UserFollowerRepository {
    public static UserFollower create(UserFollower newFollow) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            if (newFollow.getId() == null) {
                entityManager.persist(newFollow);
            } else {
                newFollow = entityManager.merge(newFollow);
            }
            entityManager.getTransaction().commit();
            return newFollow;
        }
    }

}
