package com.nima.twitter.controller;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.service.CommentService;
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
@RequestMapping("/comment")
public class CommentController {

    private final TwitService twitService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public CommentController(TwitService twitService, UserService userService, CommentService commentService) {
        this.twitService = twitService;
        this.userService = userService;
        this.commentService = commentService;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<Comment> createCommentWithUserIdAndTwitId(@RequestParam long userId,
                                                                    @RequestParam long twitId,
                                                                    @RequestParam String text){
        Twit twit = twitService.findTwit(twitId);
        User user = userService.findUser(userId);
        Date pubDate = new Date();
        return ResponseEntity.ok(commentService.create(user,twit,text, pubDate));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('comment:write')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Comment>> getAllComments(){
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('comment:read')")
    public ResponseEntity<Comment> getComment(@RequestParam long id){
        return ResponseEntity.ok(commentService.findComment(id));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('comment:write')")
    public ResponseEntity<Void> deleteComment(@RequestParam long id){
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("find-cm-between")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Comment>> findCommentsBetween(@RequestParam String s,
                                                          @RequestParam String e) throws ParseException{
        Date sdate=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(s);
        Date edate=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(e);
        return ResponseEntity.ok(commentService.findCommentsBetweenDates(sdate, edate));
    }
}
