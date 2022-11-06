package com.soteria.services;

import com.soteria.exceptions.user.EmptyPassword;
import com.soteria.exceptions.user.UserAlreadyExists;
import com.soteria.exceptions.user.UserNotFound;
import com.soteria.models.User;
import com.soteria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFound(String.format("User with ID: %s not found", id)));
    }

    public User addUser(User user) {
        Optional<User> checkUserByUserName = userRepository.findUserByUserName(user.getUserName());

        if(checkUserByUserName.isPresent()) {
            throw new UserAlreadyExists(String.format("User with the username: %s already exists", user.getUserName()));
        }

        return userRepository.save(user);
    }

    public User updateUSer(Long id, String userName, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(String.format("User with ID: %s not found", id)));
        Optional<User> checkUserByUserName = userRepository.findUserByUserName(userName);

        if(checkUserByUserName.isPresent()) {
            throw new UserAlreadyExists(String.format("User with the username: %s already exists", user.getUserName()));
        } else {
            user.setUserName(userName);
        }

        if(password != null && password.equals("")) {
            throw new EmptyPassword("You can not insert an empty password!");
        } else {
            user.setPassword(password);
        }

        return user;
    }

    public void removeUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFound(String.format("User with ID: %s not found", id)));
        userRepository.deleteById(id);
    }
}
