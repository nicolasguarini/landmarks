package bicocca2023.assignment3.service;

import bicocca2023.assignment3.controller.LandmarkController;
import bicocca2023.assignment3.model.Landmark;
import bicocca2023.assignment3.model.user.User;
import bicocca2023.assignment3.repository.LandmarkRepository;

public class LandmarkService {

    private final LandmarkRepository landmarkRepository = new LandmarkRepository();

    public Landmark createLandmark(Landmark landmark) { return landmarkRepository.save(landmark); }


}
