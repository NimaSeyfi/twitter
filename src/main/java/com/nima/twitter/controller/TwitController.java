package com.nima.twitter.controller;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.service.TwitService;
import com.nima.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/twit")
public class TwitController {

    @Autowired
    private TwitService twitService;
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<Twit> createTwitWithUserId(@RequestParam long userId,
                                                     @RequestParam String content){
        User user = userService.findUser(userId);
        Date pubDate = new Date();
        return ResponseEntity.ok(twitService.create(user, content, pubDate));
    }

    @PutMapping
    public ResponseEntity<Twit> updateTwit(@RequestParam long id,
                                           @RequestParam long userId,
                                           @RequestParam String content,
                                           @RequestParam String pubDate) throws ParseException {
        User user = userService.findUser(userId);
        Date date=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(pubDate);
        return ResponseEntity.ok(twitService.update(id, user, content, date));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Twit>> getAllTwits(){
        return ResponseEntity.ok(twitService.getAllTwits());
    }

    @GetMapping
    public ResponseEntity<Twit> getTwit(@RequestParam long id){
        return ResponseEntity.ok(twitService.findTwit(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTwit(@RequestParam long id){
        twitService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("gtl") //Get twit likes
    public ResponseEntity<List<LikeObj>> getTwitLikes(@RequestParam long id){
        return ResponseEntity.ok(twitService.getTwitLikes(id));
    }

    @GetMapping("gtc") //Get twit comments
    public ResponseEntity<List<Comment>> getTwitComments(@RequestParam long id){
        return ResponseEntity.ok(twitService.getTwitComments(id));
    }


}
