package com.nima.twitter.service.serviceImp;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.repository.UserRepository;
import com.nima.twitter.service.CommentService;
import com.nima.twitter.service.LikeObjService;
import com.nima.twitter.service.TwitService;
import com.nima.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.nima.twitter.security.UserRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private TwitService twitService;
    @Autowired
    private LikeObjService likeObjService;
    @Autowired
    private CommentService commentService;

    private final UserRepository userRepository;
    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User create(String username, String password, String phone, String email, String role) throws IOException {
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);

        role = role.toLowerCase();
        switch (role){
            case "a":
                user.setRole(UserRole.ADMIN);
                break;
            case "c":
                user.setRole(UserRole.CLIENT);
                break;
            default:
                throw new IOException("Role not found");
        }

        return userRepository.save(user);
    }

    @Override
    public User update(long id, String username, String password, String phone, String email) {
        User user = this.findUser(id);
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        User user = this.findUser(id);
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Twit> getUserTwits(long id) {
        User user = this.findUser(id);
        return twitService.getUserTwits(user);
    }

    @Override
    public List<LikeObj> getUserLikes(long id) {
        User user = this.findUser(id);
        return likeObjService.getUserLikes(user);
    }

    @Override
    public List<Comment> getUserComments(long id) {
        User user = this.findUser(id);
        return commentService.getUserComments(user);
    }

    @Override
    public User findUser(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public User findUserByPhone(String phone) {
        return userRepository.findByPhone(phone).get();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername(s);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new UsernameNotFoundException(format("User with username {0} cannot be found.", s));
        }
    }
}
