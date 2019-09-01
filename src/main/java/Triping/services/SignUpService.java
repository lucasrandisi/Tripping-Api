package Triping.services;

import Triping.utils.exceptions.HashingException;

public interface SignUpService {
    void registerUser(String username, String Password) throws HashingException;
}
