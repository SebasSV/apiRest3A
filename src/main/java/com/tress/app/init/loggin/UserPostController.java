package com.tress.app.init.loggin;

import com.tress.app.init.entities.post.Post;
import com.tress.app.init.entities.userPost.dao.UserPostDao;
import com.tress.app.init.entities.userPost.report.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserPostController {

    @Autowired
    private UserPostDao userPostDao;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping(value="/userPost")
    public List<UserPost> posts(){
        return userPostDao.findAll();
    }

    @GetMapping("/logouts")
    public void logout(@RequestParam(value = "access_token") String accessToken){
        tokenStore.removeAccessToken(tokenStore.readAccessToken(accessToken));
    }

}
