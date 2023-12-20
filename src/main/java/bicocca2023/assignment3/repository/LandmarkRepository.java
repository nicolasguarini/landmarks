package bicocca2023.assignment3.repository;

import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;

public class LandmarkRepository {

    public Landmark save(Landmark landmark){
        EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            if (landmark.getId() == null) {
                entityManager.persist(landmark);
            }
            entityManager.getTransaction().commit();
            return landmark;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e; // rethrow the exception after handling the rollback
        }
    }
}
