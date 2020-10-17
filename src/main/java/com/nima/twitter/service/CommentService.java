package com.nima.twitter.service;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.exception.Exception404;

import java.util.Date;
import java.util.List;

public interface CommentService {

    Comment create(User user, Twit twit, String text, Date date);

    Comment update(long id, User user, Twit twit, String text, Date date) throws Exception404;

    void delete(long id) throws Exception404;

    List<Comment> getAllComments();

    List<Comment> getTwitComments(Twit twit);

    List<Comment> getUserComments(User user);

    Comment findComment(long id) throws Exception404;

    List<Comment> findCommentsBetweenDates(Date s, Date e);

}
