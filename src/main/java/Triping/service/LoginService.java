package Triping.service;

import Triping.utils.exceptions.HashingException;

public interface LoginService {
    boolean validateUser(String username, String password) throws HashingException;
}
