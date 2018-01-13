package com.tress.app.init.loggin;


import com.tress.app.init.entities.user.User;
import com.tress.app.init.entities.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/home")
    @ResponseBody
    private String home(){
        return "done";
    }


}
