package com.nima.twitter.repository;

import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LikeObjRepository extends JpaRepository<LikeObj, Long> {
    List<LikeObj> findAllByUser(User user);
    List<LikeObj> findAllByTwit(Twit twit);
    List<LikeObj> findAllByDateBetween(Date s, Date e);
}
