package Triping.services.specifications;

import Triping.models.Interest;

import java.util.List;

public interface IInterestService {
    List<Interest> findAll();

    void createNewInterest(Interest interest);
}
