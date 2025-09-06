package com.hexaware.hotelbooking.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.hexaware.hotelbooking.entities.User;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);

    List<User> findByUserRole(String userRole);
}