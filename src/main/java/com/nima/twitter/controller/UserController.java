package com.nima.twitter.controller;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.exception.EntityExistException;
import com.nima.twitter.exception.Exception404;
import com.nima.twitter.exception.RoleNotFoundException;
import com.nima.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> createUser(@RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String phone,
                                           @RequestParam String email,
                                           @RequestParam String role) throws EntityExistException,
            RoleNotFoundException {
        User user = userService.create(username, password, phone, email, role);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> updateUser(@RequestParam long id,
                                           @RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String phone,
                                           @RequestParam String email) throws Exception404 {
        return ResponseEntity.ok(userService.update(id, username, password, phone, email));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getUser(@RequestParam long id) throws Exception404 {
        return ResponseEntity.ok(userService.findUser(id));
    }

    @GetMapping("/find-by-phone")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUserByPhone(@RequestParam String phone){
        return ResponseEntity.ok(userService.findUserByPhone(phone));
    }

    @GetMapping("/find-by-email")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }


    @DeleteMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Void> deleteUser(@RequestParam long id) throws Exception404 {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/gut") //Get user twits
    @PreAuthorize("hasAnyAuthority({'user:read','twit:read'})")
    public ResponseEntity<List<Twit>> getUserTwits(@RequestParam long id) throws Exception404 {
        return ResponseEntity.ok(userService.getUserTwits(id));
    }

    @GetMapping("/gul") //Get user likes
    @PreAuthorize("hasAnyAuthority({'user:read','twit:read','like:read'})")
    public ResponseEntity<List<LikeObj>> getUserLikes(@RequestParam long id) throws Exception404 {
        return ResponseEntity.ok(userService.getUserLikes(id));
    }

    @GetMapping("/guc") //Get user comments
    @PreAuthorize("hasAnyAuthority({'user:read','twit:read','comment:read'})")
    public ResponseEntity<List<Comment>> getUserComments(@RequestParam long id) throws Exception404 {
        return ResponseEntity.ok(userService.getUserComments(id));
    }
}
