package com.tress.app.init.user.service.impl;

import com.tress.app.init.config.WebMvcConfig;
import com.tress.app.init.role.Role;
import com.tress.app.init.role.RoleRepository;
import com.tress.app.init.user.User;
import com.tress.app.init.user.UserRepository;
import com.tress.app.init.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
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
