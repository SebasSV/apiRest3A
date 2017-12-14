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
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> users(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createNewUser(@Valid User user, BindingResult bindingResult) {
        Map<String, Object> result = new HashMap<>();
        User userExists = userService.findByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            result.put("status", 0);
        } else {
            user.setRoles(Arrays.asList(new Role("USER"), new Role("ACTUATOR")));
            userService.saveUser(user);
            result.put("successMessage", "User has been registered successfully");
            result.put("status", 1);

        }
        return result;
    }


}
