package com.soteria.controllers;

import com.soteria.models.Role;
import com.soteria.models.User;
import com.soteria.payload.JwtAuthenticationResponse;
import com.soteria.payload.UserDTO;
import com.soteria.security.jwt.JwtTokenProvider;
import com.soteria.services.RoleService;
import com.soteria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  User controller
 *  @author  Ramphy Aquino Nova
 *  @version 2022.11.18
 */
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService,
                          RoleService roleService,
                          JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Returns a new JWT token
     * @param userDTO user data
     * @return ResponseEntity<JwtAuthenticationResponse>
     */
    @PostMapping("/log-in")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDTO.getUserName(), userDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    /**
     * Returns the new created user data
     * @param userDTO user data
     * @return ResponseEntity<User>
     */
    @PostMapping("/sign-in")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
        Role role = roleService.getRole("ROLE_STANDARD");
        String userName = userDTO.getUserName();
        String password = passwordEncoder.encode(userDTO.getPassword());

        User user = new User(userName, password, new ArrayList<>());
        user.setRole(role);
        User newUser = userService.addUser(user);

        return ResponseEntity.ok(newUser);
    }

    /**
     * Returns all users
     * @return List<User>
     */
    @GetMapping(path = "/all")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * Returns a user by given user id
     * @param id user id
     * @return User
     */
    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    /**
     * Create a new user and returns the created user
     * @param user user data
     * @return User
     */
    @PostMapping(path = "/add")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * Update user data and returns the updated user
     * @param id user id
     * @param userName new username
     * @param password new password
     * @return User
     */
    @PutMapping(path = "/update/{id}")
    public User updateUser(@PathVariable("id") Long id,
                           @RequestParam("userName") String userName,
                           @RequestParam("password") String password) {

        return userService.updateUSer(id, userName, password);
    }

    /**
     * Delete a user by given id
     * @param id user id
     */
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
    }
}
