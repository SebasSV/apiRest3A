package com.tress.app.init.loggin;


import com.tress.app.init.config.CustomUserDetails;
import com.tress.app.init.entities.Post;
import com.tress.app.init.entities.service.PostService;
import com.tress.app.init.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(value="/posts")
    public List<Post> posts(){
        return postService.getAllPosts();
    }

    @PostMapping(value="/post")
    public String publishPost(@RequestBody Post post){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(post.getCreatedDate() == null)
            post.setCreatedDate(new Date());
        post.setUser(userService.getUser(userDetails.getUsername()));
        postService.insert(post);
        return "Post was published";
    }


}
