package com.nima.twitter.controller;

import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.exception.Exception404;
import com.nima.twitter.service.LikeObjService;
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
@RequestMapping("/like")
public class LikeController {

    private final TwitService twitService;
    private final UserService userService;
    private final LikeObjService likeObjService;

    @Autowired
    public LikeController(TwitService twitService, UserService userService, LikeObjService likeObjService) {
        this.twitService = twitService;
        this.userService = userService;
        this.likeObjService = likeObjService;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('like:write')")
    public ResponseEntity<LikeObj> createLikeWithUserIdAndTwitId(@RequestParam long userId,
                                                        @RequestParam long twitId) throws Exception404 {
        Twit twit = twitService.findTwit(twitId);
        User user = userService.findUser(userId);
        Date pubDate = new Date();
        return ResponseEntity.ok(likeObjService.create(user,twit, pubDate));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('like:write')")
    public ResponseEntity<LikeObj> updateLike(@RequestParam long id,
                                           @RequestParam long userId,
                                           @RequestParam long twitId,
                                           @RequestParam String pubDate) throws ParseException, Exception404 {
        Twit twit = twitService.findTwit(twitId);
        User user = userService.findUser(userId);
        Date date=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(pubDate);
        return ResponseEntity.ok(likeObjService.update(id, user, twit, date));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<LikeObj>> getAllLikes(){
        return ResponseEntity.ok(likeObjService.getAllLikes());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('like:read')")
    public ResponseEntity<LikeObj> getLike(@RequestParam long id) throws Exception404 {
        return ResponseEntity.ok(likeObjService.findLike(id));
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('like:write')")
    public ResponseEntity<Void> deleteLike(@RequestParam long id) throws Exception404 {
        likeObjService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("find-likes-between")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<LikeObj>> findLikesBetween(@RequestParam String s,
                                                          @RequestParam String e) throws ParseException{
        Date sdate=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(s);
        Date edate=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").parse(e);
        return ResponseEntity.ok(likeObjService.findLikesBetweenDate(sdate, edate));
    }

}
