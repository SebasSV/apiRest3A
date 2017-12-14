package com.tress.app.init.entities.service;

import com.tress.app.init.entities.Post;
import javafx.geometry.Pos;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    void insert(Post post);
}
