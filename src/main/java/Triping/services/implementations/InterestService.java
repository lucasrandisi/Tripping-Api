package Triping.services.implementations;


import Triping.models.Interest;
import Triping.repositories.InterestRepository;
import Triping.services.specifications.IInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService implements IInterestService {

    @Autowired
    private InterestRepository interestRepository;

    @Override
    public List<Interest> findAll() {
        return interestRepository.findAll();
    }

    @Override
    public void createNewInterest(Interest interest) {
        interestRepository.save(interest);
    }
}
