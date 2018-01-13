package com.tress.app.init.loggin;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FacebookController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        RedirectView redirectView = new RedirectView();
        Map<String, Object> result = new HashMap<>();
        redirectView.setUrl("localhost:3000");
        redirectView.setAttributesMap(result);
        return principal;
    }
}
