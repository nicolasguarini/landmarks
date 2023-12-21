package bicocca2023.assignment3.repository;

import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;

public class LandmarkRepository {

    public Landmark save(Landmark landmark){
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()){
            entityManager.getTransaction().begin();
            if (landmark.getId() == null) {
                entityManager.persist(landmark);
            }
            entityManager.getTransaction().commit();
            return landmark;
        }
    }
}
