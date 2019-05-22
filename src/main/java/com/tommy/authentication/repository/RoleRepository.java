package com.tommy.authentication.repository;

import com.tommy.authentication.model.Role;
import com.tommy.authentication.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
