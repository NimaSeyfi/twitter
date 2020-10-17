package com.nima.twitter.service.serviceImp;

import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.exception.Exception404;
import com.nima.twitter.repository.LikeObjRepository;
import com.nima.twitter.service.LikeObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LikeObjServiceImp implements LikeObjService {

    private final LikeObjRepository likeObjRepository;
    @Autowired
    public LikeObjServiceImp(LikeObjRepository likeObjRepository) {
        this.likeObjRepository = likeObjRepository;
    }


    @Override
    public LikeObj create(User user, Twit twit, Date date) {
        LikeObj like = new LikeObj();
        like.setUser(user);
        like.setTwit(twit);
        like.setDate(date);
        return likeObjRepository.save(like);
    }

    @Override
    public LikeObj update(long id, User user, Twit twit, Date date) throws Exception404 {
        LikeObj like = this.findLike(id);
        like.setUser(user);
        like.setTwit(twit);
        like.setDate(date);
        return likeObjRepository.save(like);
    }

    @Override
    public void delete(long id) throws Exception404 {
        LikeObj like = this.findLike(id);
        likeObjRepository.delete(like);
    }

    @Override
    public List<LikeObj> getAllLikes() {
        return likeObjRepository.findAll();
    }

    @Override
    public List<LikeObj> getTwitLikes(Twit twit) {
        return likeObjRepository.findAllByTwit(twit);
    }

    @Override
    public List<LikeObj> getUserLikes(User user) {
        return likeObjRepository.findAllByUser(user);
    }

    @Override
    public LikeObj findLike(long id) throws Exception404 {
        return likeObjRepository.findById(id)
                .orElseThrow(()->new Exception404(String.format("LikeObj not found with id : %d",id)));
    }

    @Override
    public List<LikeObj> findLikesBetweenDate(Date s, Date e) {
        return likeObjRepository.findAllByDateBetween(s,e);
    }
}
