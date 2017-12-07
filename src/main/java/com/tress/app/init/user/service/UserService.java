package com.tress.app.init.user.service;

import com.tress.app.init.user.User;

public interface UserService {

    public User findUserByEmail(String email);

    public void saveUser(User user);
}
