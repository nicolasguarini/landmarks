package bicocca2023.assignment3.service;

import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.repository.LandmarkRepository;

import java.util.List;
import java.util.UUID;

public class LandmarkService {
    private final LandmarkRepository landmarkRepository = new LandmarkRepository();

    public Landmark createLandmark(Landmark landmark) {
        return landmarkRepository.save(landmark);
    }

    public List<Landmark> getAllLandmarks() { return landmarkRepository.findAll(); }

    public void deleteLandmark(UUID id) {
        landmarkRepository.delete(id);
    }

    public Landmark getLandmarkById(UUID id) {
        return landmarkRepository.findById(id);
    }

    public Landmark updateLandmark(Landmark landmark) {
        return landmarkRepository.update(landmark);
    }
}
