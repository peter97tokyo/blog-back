package kr.peter.tokyo.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.peter.tokyo.blog.entity.User;
import kr.peter.tokyo.blog.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User save(User user) {
        return userRepository.save(user);
    }

    public User userByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User userByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
