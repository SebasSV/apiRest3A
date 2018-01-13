package com.tress.app.init.entities.post.service.impl;


import com.tress.app.init.entities.post.Post;
import com.tress.app.init.entities.post.PostRepository;
import com.tress.app.init.entities.post.service.PostService;
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
