package com.nima.twitter.service;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.exception.EntityExistException;
import com.nima.twitter.exception.Exception404;
import com.nima.twitter.exception.RoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {

    User create(String username, String password, String phone, String email, String role) throws EntityExistException,
            RoleNotFoundException;

    User update(long id, String username, String password, String phone, String email) throws Exception404;

    void delete(long id) throws Exception404;

    List<User> getAllUsers();

    List<Twit> getUserTwits(long id) throws Exception404;

    List<LikeObj> getUserLikes(long id) throws Exception404;

    List<Comment> getUserComments(long id) throws Exception404;

    User findUser(long id) throws Exception404;

    User findUserByEmail(String email);

    User findUserByPhone(String phone);

    User findUserByUsername(String username);

    void createAdmin() throws IOException;

}
