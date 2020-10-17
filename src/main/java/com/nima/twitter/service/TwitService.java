package com.nima.twitter.service;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.exception.Exception404;

import java.util.Date;
import java.util.List;

public interface TwitService {

    Twit create(User user, String content, Date pubDate);

    Twit update(long id, User user, String content, Date pubDate) throws Exception404;

    void delete(long id) throws Exception404;

    List<Twit> getAllTwits();

    List<Twit> getUserTwits(User user);

    Twit findTwit(long id) throws Exception404;

    List<LikeObj> getTwitLikes(long id) throws Exception404;

    List<Comment> getTwitComments(long id) throws Exception404;

    User getTwitUser(long id) throws Exception404;

}
