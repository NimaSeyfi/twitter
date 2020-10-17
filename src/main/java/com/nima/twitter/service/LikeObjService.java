package com.nima.twitter.service;

import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.exception.Exception404;

import java.util.Date;
import java.util.List;

public interface LikeObjService {

    LikeObj create(User user, Twit twit, Date date);

    LikeObj update(long id, User user, Twit twit, Date date) throws Exception404;

    void delete(long id) throws Exception404;

    List<LikeObj> getAllLikes();

    List<LikeObj> getTwitLikes(Twit twit);

    List<LikeObj> getUserLikes(User user);

    LikeObj findLike(long id) throws Exception404;

    List<LikeObj> findLikesBetweenDate(Date s, Date e);

}
