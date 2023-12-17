package bicocca2023.assignment3.repository;


import bicocca2023.assignment3.model.Point;
import bicocca2023.assignment3.model.User;
import bicocca2023.assignment3.util.PersistenceManager;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class PointRepository {

    public List<Point> findAll(){
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            return entityManager.createQuery("SELECT p FROM Point p", Point.class).getResultList();
        }
    }

    public Point findByCoordinates(double longitude, double latitude){
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            List<Point> result = entityManager.createQuery(
                            "SELECT p FROM Point p WHERE p.longitude = :longitude AND p.latitude = :latitude", Point.class)
                    .setParameter("longitude", longitude)
                    .setParameter("latitude", latitude)
                    .getResultList();

            return result.isEmpty() ? null : result.get(0);
        }
    }

    public Point save(Point point) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            if (point.getLongitude() == 0 && point.getLatitude() == 0) {
                entityManager.persist(point);
            } else {
                point = entityManager.merge(point);
            }
            entityManager.getTransaction().commit();
            return point;
        }
    }

    public void delete(double longitude, double latitude) {
        try (EntityManager entityManager = PersistenceManager.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            Point point = findByCoordinates(longitude, latitude);

            if (point != null) {
                entityManager.remove(point);
            }

            entityManager.getTransaction().commit();
        }
    }

}
