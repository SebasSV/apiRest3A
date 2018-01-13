package com.tress.app.init.loggin;

import com.tress.app.init.entities.post.Post;
import com.tress.app.init.entities.userPost.dao.UserPostDao;
import com.tress.app.init.entities.userPost.report.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserPostController {

    @Autowired
    private UserPostDao userPostDao;

    @GetMapping(value="/userPost")
    public List<UserPost> posts(){
        return userPostDao.findAll();
    }

}
