package com.task_userservice.repository;

import com.task_userservice.entity.Enum.RoleName;
import com.task_userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

}
