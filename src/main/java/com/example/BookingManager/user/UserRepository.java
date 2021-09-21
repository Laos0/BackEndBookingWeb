package com.example.BookingManager.user;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.NamedQuery;
import java.util.Optional;

@NamedQuery(name = "User.findByEmail",
        query = "select u from User u where u.email = ?1")
public interface UserRepository extends JpaRepository<User, Long> {

    // spring will create a query to delete the user with its id
    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);
    Optional<User> findByEmail(String email);
}
