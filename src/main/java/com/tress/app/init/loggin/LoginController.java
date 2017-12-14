package com.tress.app.init.loggin;


import com.tress.app.init.role.Role;
import com.tress.app.init.user.User;
import com.tress.app.init.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/home")
    @ResponseBody
    private String home(){
        return "done";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    private String login(){
        return "done";
    }

}
