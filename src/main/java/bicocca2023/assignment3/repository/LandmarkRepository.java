package bicocca2023.assignment3.repository;

import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
@Transactional
public class LandmarkRepository {
    public LandmarkRepository() {}

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

    public void delete(UUID id) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            try {
                entityManager.getTransaction().begin();

                Landmark landmark = entityManager.find(Landmark.class, id);

                if (landmark != null) {
                    entityManager.remove(landmark);
                } else {
                    System.err.println("Landmark not found for ID: " + id);
                }

                entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());

                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
            }
        }
    }

    public Landmark update(Landmark landmark) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            if (landmark.getId() != null) {
                landmark = entityManager.merge(landmark);
            }

            entityManager.getTransaction().commit();
            return landmark;
        }
    }

    public Landmark findById(UUID id) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            return entityManager.find(Landmark.class, id);
        }
    }

    public List<Landmark> findAll() {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Landmark> landmarks = entityManager.createQuery("SELECT l FROM Landmark l", Landmark.class).getResultList();
            entityManager.getTransaction().commit();
            return landmarks;
        } catch (Exception e) {
            System.err.println();
            return Collections.emptyList();
        }
    }
}
