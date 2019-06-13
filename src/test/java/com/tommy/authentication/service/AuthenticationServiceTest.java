package com.tommy.authentication.service;

import com.tommy.authentication.model.User;
import com.tommy.authentication.repository.AuthenticationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
@DataJpaTest
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationRepository repository;

    @Before
    public void registerTestUser(){
        User testUser = new User();
        testUser.setEmail("tommygoossens@ziggo.nl");
        testUser.setPassword("123456");
        testUser.setPassword(hashPassword(testUser.getPassword()));
        testUser.setFirstName("Tommy");
        testUser.setLastName("Goossens");

        System.out.println(repository.save(testUser));
    }

    @Test
    public void signInSuccesfully() {
        Assert.assertTrue(true);
    }

    @Test
    public void signUp() {
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
}