package com.tress.app.init.entities.user.service.impl;

import com.tress.app.init.config.WebMvcConfig;
import com.tress.app.init.entities.user.User;
import com.tress.app.init.entities.user.UserRepository;
import com.tress.app.init.entities.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebMvcConfig webMvcConfig;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }


    @Override
    public void saveUser(User user) {
        user.setPassword(webMvcConfig.passwordEncoder().encode(user.getPassword()));
        user.setActive(1);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
