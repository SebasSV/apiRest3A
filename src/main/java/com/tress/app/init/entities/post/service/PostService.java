package com.tress.app.init.entities.post.service;

import com.tress.app.init.entities.post.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    void insert(Post post);
}
