package com.example.BookingManager.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // spring will create a query to delete the user with its id
    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);
}
