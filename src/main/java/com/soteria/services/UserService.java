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

/**
 *  User service
 *  @author  Ramphy Aquino Nova
 *  @version 2022.11.14
 */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get all users in Soteria
     *
     * @return List<User>
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user by given id
     *
     * @param id User id
     * @return User
     */
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFound(String.format("User with ID: %s not found", id)));
    }

    /**
     * Get user by username
     *
     * @param userName User username
     * @return User
     */
    public User getUser(String userName) {
        return userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new UserNotFound(String.format("User with username: %s not found", userName)));
    }

    /**
     * Add user. If the new user has the same
     * username of an existing user, the new user
     * will not be created
     *
     * @param user User
     * @return User
     */
    public User addUser(User user) {
        Optional<User> checkUserByUserName = userRepository.findUserByUserName(user.getUserName());

        if(checkUserByUserName.isPresent()) {
            throw new UserAlreadyExists(String.format("User with the username: %s already exists", user.getUserName()));
        }

        return userRepository.save(user);
    }

    /**
     * Update user data. The username can be updated only
     * if the username specified does not exist and the password
     * can be updated if is not empty
     *
     * @param id User id
     * @param userName New username
     * @param password New password
     * @return User
     */
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

    /**
     * Delete user by given id
     *
     * @param id User id
     */
    public void removeUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFound(String.format("User with ID: %s not found", id)));
        userRepository.deleteById(id);
    }
}
