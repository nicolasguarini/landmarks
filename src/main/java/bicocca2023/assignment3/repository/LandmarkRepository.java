package bicocca2023.assignment3.repository;

import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LandmarkRepository {
    public LandmarkRepository(){}

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

    public List<Landmark> findAll() {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery("SELECT l FROM Landmark l", Landmark.class).getResultList();
        }
    }
}
