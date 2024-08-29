package com.task_userservice.repository;

import com.task_userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

//    @Query("SELECT u FROM User u WHERE u.username LIKE %:prefix%")
//    List<User> findByUsernamePrefix(String prefix);

    List<User> findByFirstNameContainingOrLastNameContainingOrUserName(String firstName, String lastName, String userName);

//    List<User> searchByKeyword(String keyWord);
}
