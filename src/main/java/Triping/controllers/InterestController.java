package Triping.controllers;


import Triping.models.Interest;
import Triping.services.specifications.IInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InterestController {

    @Autowired
    private IInterestService interestService;

    @GetMapping("/interest/all")
    public List<Interest> findAll(){
        return interestService.findAll();
    }

    @PostMapping("/interest/add")
    public void createNewInterest(@RequestBody Interest interest){
        interestService.createNewInterest(interest);
    }


}
