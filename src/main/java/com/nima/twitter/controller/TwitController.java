package com.nima.twitter.controller;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.exception.Exception404;
import com.nima.twitter.service.TwitService;
import com.nima.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/twit")
public class TwitController {

    private final TwitService twitService;
    private final UserService userService;

    @Autowired
    public TwitController(TwitService twitService, UserService userService) {
        this.twitService = twitService;
        this.userService = userService;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('twit:write')")
    public ResponseEntity<Twit> createTwitWithUserId(@RequestParam long userId,
                                                     @RequestParam String content) throws Exception404 {
        User user = userService.findUser(userId);
        Date pubDate = new Date();
        return ResponseEntity.ok(twitService.create(user, content, pubDate));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('twit:write')")
    public ResponseEntity<Twit> updateTwit(@RequestParam long id,
                                           @RequestParam long userId,
                                           @RequestParam String content,
                                           @RequestParam String pubDate) throws ParseException, Exception404 {
        User user = userService.findUser(userId);
        Date date=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(pubDate);
        return ResponseEntity.ok(twitService.update(id, user, content, date));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Twit>> getAllTwits(){
        return ResponseEntity.ok(twitService.getAllTwits());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('twit:read')")
    public ResponseEntity<Twit> getTwit(@RequestParam long id) throws Exception404 {
            return ResponseEntity.ok(twitService.findTwit(id));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTwit(@RequestParam long id) throws Exception404 {
        twitService.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("gtl") //Get twit likes
    @PreAuthorize("hasAuthority('twit:read')")
    public ResponseEntity<List<LikeObj>> getTwitLikes(@RequestParam long id) throws Exception404 {
        return ResponseEntity.ok(twitService.getTwitLikes(id));
    }

    @GetMapping("gtc") //Get twit comments
    @PreAuthorize("hasAuthority('twit:read')")
    public ResponseEntity<List<Comment>> getTwitComments(@RequestParam long id) throws Exception404 {
        return ResponseEntity.ok(twitService.getTwitComments(id));
    }


}
