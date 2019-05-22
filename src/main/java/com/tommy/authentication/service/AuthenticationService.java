package com.tommy.authentication.service;

import com.tommy.authentication.exception.DuplicateEntity;
import com.tommy.authentication.exception.NotExistingEntity;
import com.tommy.authentication.exception.PasswordIncorrectException;
import com.tommy.authentication.model.*;
import com.tommy.authentication.repository.AuthenticationRepository;
import com.tommy.authentication.repository.RoleRepository;
import com.tommy.authentication.security.JwtProvider;
import com.tommy.authentication.security.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthenticationService {

    private final RestTemplate restTemplate;

    private AuthenticationRepository AuthRepo;
    private RoleRepository roleRepo;

    private JwtProvider jwtProvider = new JwtProvider(this);

    @Autowired
    public AuthenticationService(AuthenticationRepository AuthRepo, RoleRepository roleRepo, RestTemplate restTemplate) {
        this.AuthRepo = AuthRepo;
        this.roleRepo = roleRepo;
        this.restTemplate = restTemplate;
    }

    /**
     * Registers a new user in the database.
     *
     * @param participant containing a password and a email
     * @return 200: if email already exitst, accepted when the user is registered and a badrequest whenever the data is not correct formatted
     */
    public ResponseEntity<?> register(RegisterParticipant participant) {
        AuthRepo.findUserByEmail(participant.getEmail()).ifPresent(u -> { throw new DuplicateEntity(participant.getEmail()); });

        participant.setPassword(hashPassword(participant.getPassword()));


        User registerUser = new User(participant.getEmail(), participant.getPassword(),participant.getFirstname(),participant.getLastname(),generateDefaultRoleSet());
        HttpEntity<User> request = new HttpEntity<>(registerUser);

        AuthRepo.save(registerUser);
        restTemplate.postForEntity("http://user-service/profile", request, Void.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("[User is registered] : " + participant.getEmail());
    }


    /**
     * Method to authenticate the user, if the provided credentials are correct.
     * Might throw a NotExistingEntity exception when the email is not to be found, or a PasswordIncorrectException when the credentials don't match
     * @param participant object containing a email and password provided by the user to authenticate
     * @return jwt token containing all relevant information when the user details are correct
     */
    public ResponseEntity<JwtResponse> login(AuthenticationParticipant participant) {
        User user = AuthRepo.findUserByEmail(participant.getEmail()).orElseThrow(() -> new NotExistingEntity(participant.getEmail()));

        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoles())
            roles.add(role.getRoleType().name());

        if (comparePasswords(participant.getPassword(), user.getPassword())) {
            return ResponseEntity.ok().body(new JwtResponse(jwtProvider.createToken(user,roles)));
        }
        throw new PasswordIncorrectException();
    }

    /**
     * Hashes a password using BCrypts encoding algorithm
     *
     * @param plaintextPassword The password in plain text
     * @return Hashed password
     */
    private String hashPassword(String plaintextPassword) {
        return new BCryptPasswordEncoder().encode(plaintextPassword);
    }

    /**
     * Compares if the plain text password matches the hashed password after hashing
     *
     * @param plainTextPassword The password in plain text
     * @param hashedPassword    The users hashed password
     * @return true or false based on if it's a match
     */
    private boolean comparePasswords(String plainTextPassword, String hashedPassword) {
        return new BCryptPasswordEncoder().matches(plainTextPassword, hashedPassword);
    }

    /**
     * Returns an User object from the database if the one requested exists
     * @param email which is used to identify the user
     * @return UserDetails class
     */
    public UserDetails getUserDetailsByEmail(String email) {
        User user = AuthRepo.findUserByEmail(email).orElseThrow(() -> new NotExistingEntity(email));

        return UserDetailImpl.builder(user);
    }

    /**
     * Generates a default User role when registering
     * @return Set of Roles containing one role: ROLE_USER
     */
    private Set<Role> generateDefaultRoleSet() {
        Set<Role> roleSet = new HashSet<>();
        roleRepo.findByRoleType(RoleType.ROLE_USER).ifPresent(roleSet::add);
        return roleSet;
    }
}
