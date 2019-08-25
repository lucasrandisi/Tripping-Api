package Triping.service;

import Triping.utils.exceptions.HashingException;

public interface SignInService {
    void registerUser(String username, String Password) throws HashingException;
}
