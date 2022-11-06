package com.soteria.controllers;

import com.soteria.models.Role;
import com.soteria.models.User;
import com.soteria.payload.JwtAuthenticationResponse;
import com.soteria.payload.UserAuthO;
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

    @PostMapping("/log-in")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody UserAuthO userAuthO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userAuthO.getUserName(), userAuthO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping("sign-in")
    public ResponseEntity<?> registerUser(@RequestBody UserAuthO userAuthO) {
        Role role = roleService.getRole("ROLE_STANDARD");
        String userName = userAuthO.getUserName();
        String password = passwordEncoder.encode(userAuthO.getPassword());

        User user = new User(userName, password, new ArrayList<>());
        user.setRole(role);
        userService.addUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping(path = "/add")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping(path = "/update/{id}")
    public User updateUser(@PathVariable("id") Long id,
                           @RequestParam("userName") String userName,
                           @RequestParam("password") String password) {

        return userService.updateUSer(id, userName, password);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
    }
}
