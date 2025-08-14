package com.hexaware.OnlineFoodDeliverySys.repository;

import com.hexaware.OnlineFoodDeliverySys.entities.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);

    List<User> findByUserRole(String userRole);
}