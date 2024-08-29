package com.task_userservice.repository;

import com.task_userservice.entity.Enum.RoleName;
import com.task_userservice.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRepository extends JpaRepository<Access, Long> {
    List<Access> findByRoles_Name(RoleName roleName);
}