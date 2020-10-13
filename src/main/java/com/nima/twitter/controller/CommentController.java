package com.nima.twitter.controller;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.service.CommentService;
import com.nima.twitter.service.LikeObjService;
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
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private TwitService twitService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;


    @PostMapping
    public ResponseEntity<Comment> createCommentWithUserIdAndTwitId(@RequestParam long userId,
                                                                    @RequestParam long twitId,
                                                                    @RequestParam String text){
        Twit twit = twitService.findTwit(twitId);
        User user = userService.findUser(userId);
        Date pubDate = new Date();
        return ResponseEntity.ok(commentService.create(user,twit,text, pubDate));
    }

    @PutMapping
    public ResponseEntity<Comment> updateComment(@RequestParam long id,
                                              @RequestParam long userId,
                                              @RequestParam long twitId,
                                              @RequestParam String text,
                                              @RequestParam String pubDate) throws ParseException {
        Twit twit = twitService.findTwit(twitId);
        User user = userService.findUser(userId);
        Date date=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(pubDate);
        return ResponseEntity.ok(commentService.update(id, user, twit, text, date));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments(){
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping
    public ResponseEntity<Comment> getComment(@RequestParam long id){
        return ResponseEntity.ok(commentService.findComment(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@RequestParam long id){
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("find-cm-between")
    public ResponseEntity<List<Comment>> findCommentsBetween(@RequestParam String s,
                                                          @RequestParam String e) throws ParseException{
        Date sdate=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(s);
        Date edate=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(e);
        return ResponseEntity.ok(commentService.findCommentsBetweenDates(sdate, edate));
    }
}
