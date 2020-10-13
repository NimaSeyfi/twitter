package com.nima.twitter.service;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;

import java.util.Date;
import java.util.List;

public interface TwitService {

    Twit create(User user, String content, Date pubDate);

    Twit update(long id, User user, String content, Date pubDate);

    void delete(long id);

    List<Twit> getAllTwits();

    List<Twit> getUserTwits(User user);

    Twit findTwit(long id);

    List<LikeObj> getTwitLikes(long id);

    List<Comment> getTwitComments(long id);

    User getTwitUser(long id);

}
