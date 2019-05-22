package com.tommy.authentication.repository;

import com.tommy.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<User,Long> {
    boolean existsUserByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
