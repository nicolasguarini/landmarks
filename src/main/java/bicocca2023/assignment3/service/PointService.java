package bicocca2023.assignment3.service;

import bicocca2023.assignment3.model.Point;
import bicocca2023.assignment3.repository.PointRepository;

import java.util.List;

public class PointService {

    private final PointRepository PointRepository = new PointRepository();

    public List<Point> getAllPoints(){
        return PointRepository.findAll();
    }

    public Point getPointByCoordinates(double x, double y){
        return PointRepository.findByCoordinates(x, y);
    }

    public Point createPoint(Point point){
        return PointRepository.save(point);
    }


}
