package bicocca2023.assignment3.repository;

import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

public class LandmarkRepository {
    public LandmarkRepository() {
    }

    public Landmark save(Landmark landmark) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
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

    public void delete(UUID id) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            try {
                entityManager.getTransaction().begin();

                Landmark landmark = entityManager.find(Landmark.class, id);

                if (landmark != null) {
                    System.out.println("RIMOSSO:" + landmark.getId());
                    entityManager.remove(landmark);
                } else {
                    System.err.println("Landmark not found for ID: " + id);
                }

                entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.err.println("Error in LandmarkRepository");
                e.printStackTrace(); // Log the exception stack trace for debugging
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
            }
        }
    }


    public Landmark findById(UUID id) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            return entityManager.find(Landmark.class, id);
        }
    }
}
