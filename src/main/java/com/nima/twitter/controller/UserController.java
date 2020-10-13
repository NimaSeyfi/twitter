package com.nima.twitter.controller;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    //@PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<User> createUser(@RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String phone,
                                           @RequestParam String email,
                                           @RequestParam String role) throws IOException {
        User user = userService.create(username, password, phone, email, role);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestParam long id,
                                           @RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String phone,
                                           @RequestParam String email){
        return ResponseEntity.ok(userService.update(id, username, password, phone, email));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam long id){
        return ResponseEntity.ok(userService.findUser(id));
    }

    @GetMapping("/find-by-phone")
    public ResponseEntity<User> getUserByPhone(@RequestParam String phone){
        return ResponseEntity.ok(userService.findUserByPhone(phone));
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/gut") //Get user twits
    public ResponseEntity<List<Twit>> getUserTwits(@RequestParam long id){
        return ResponseEntity.ok(userService.getUserTwits(id));
    }

    @GetMapping("/gul") //Get user likes
    public ResponseEntity<List<LikeObj>> getUserLikes(@RequestParam long id){
        return ResponseEntity.ok(userService.getUserLikes(id));
    }

    @GetMapping("/guc") //Get user comments
    public ResponseEntity<List<Comment>> getUserComments(@RequestParam long id){
        return ResponseEntity.ok(userService.getUserComments(id));
    }
}
