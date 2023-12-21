package bicocca2023.assignment3.service;

import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.repository.LandmarkRepository;

import java.util.List;

public class LandmarkService {
    private final LandmarkRepository landmarkRepository = new LandmarkRepository();

    public Landmark createLandmark(Landmark landmark) {
        return landmarkRepository.save(landmark);
    }

    public List<Landmark> getAllLandmarks() {
        return landmarkRepository.findAll();
    }
}
