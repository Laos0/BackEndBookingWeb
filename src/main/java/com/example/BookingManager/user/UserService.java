package com.example.BookingManager.user;

import com.example.BookingManager.consoleTextMod.ColorText;
import com.example.BookingManager.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public User findUserById(Long id){
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException(ColorText.ANSI_RED + "User by id: " + id + "was not found" +ColorText.ANSI_RESET));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Email does not exist"));
    }

    public void deleteUser(Long id){
        userRepository.deleteUserById(id);
    }
}
