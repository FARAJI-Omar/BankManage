package service;

import model.Person;

import java.util.Optional;

public interface AuthService {
    Optional<Person> login(String email, String password);
}
