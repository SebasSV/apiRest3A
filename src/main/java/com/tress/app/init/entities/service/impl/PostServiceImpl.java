package com.tress.app.init.entities.service.impl;


import com.tress.app.init.entities.Post;
import com.tress.app.init.entities.PostRepository;
import com.tress.app.init.entities.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postService")
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public void insert(Post post) {
        postRepository.save(post);
    }

}
