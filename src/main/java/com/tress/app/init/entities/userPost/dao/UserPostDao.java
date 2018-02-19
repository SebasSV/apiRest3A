package com.tress.app.init.entities.userPost.dao;

import com.tress.app.init.entities.userPost.report.UserPost;

import java.util.List;

public interface UserPostDao {

    List<UserPost> findAll();

    List<UserPost> findByUserId(Integer userId);
}
