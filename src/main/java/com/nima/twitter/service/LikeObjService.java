package com.nima.twitter.service;

import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;

import java.util.Date;
import java.util.List;

public interface LikeObjService {

    LikeObj create(User user, Twit twit, Date date);

    LikeObj update(long id, User user, Twit twit, Date date);

    void delete(long id);

    List<LikeObj> getAllLikes();

    List<LikeObj> getTwitLikes(Twit twit);

    List<LikeObj> getUserLikes(User user);

    LikeObj findLike(long id);

    List<LikeObj> findLikesBetweenDate(Date s, Date e);

}
