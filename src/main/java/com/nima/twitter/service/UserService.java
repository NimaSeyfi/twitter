package com.nima.twitter.service;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User create(String username, String password, String phone, String email, String role) throws IOException;

    User update(long id, String username, String password, String phone, String email);

    void delete(long id);

    List<User> getAllUsers();

    List<Twit> getUserTwits(long id);

    List<LikeObj> getUserLikes(long id);

    List<Comment> getUserComments(long id);

    User findUser(long id);

    User findUserByEmail(String email);

    User findUserByPhone(String phone);

}
