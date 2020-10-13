package com.nima.twitter.service.serviceImp;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.LikeObj;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.repository.TwitRepository;
import com.nima.twitter.service.CommentService;
import com.nima.twitter.service.LikeObjService;
import com.nima.twitter.service.TwitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TwitServiceImp implements TwitService {

    @Autowired
    private LikeObjService likeObjService;
    @Autowired
    private CommentService commentService;

    private final TwitRepository twitRepository;
    @Autowired
    public TwitServiceImp(TwitRepository twitRepository) {
        this.twitRepository = twitRepository;
    }


    @Override
    public Twit create(User user, String content, Date pubDate) {
        Twit twit = new Twit();
        twit.setUser(user);
        twit.setContent(content);
        twit.setPubDate(pubDate);
        return twitRepository.save(twit);
    }

    @Override
    public Twit update(long id, User user, String content, Date pubDate) {
        Twit twit = this.findTwit(id);
        twit.setUser(user);
        twit.setContent(content);
        twit.setPubDate(pubDate);
        return twitRepository.save(twit);
    }

    @Override
    public void delete(long id) {
        Twit twit = this.findTwit(id);
        twitRepository.delete(twit);
    }

    @Override
    public List<Twit> getAllTwits() {
        return twitRepository.findAll();
    }

    @Override
    public List<Twit> getUserTwits(User user) {
        return twitRepository.findAllByUser(user);
    }

    @Override
    public Twit findTwit(long id) {
        return twitRepository.findById(id).get();
    }

    @Override
    public List<LikeObj> getTwitLikes(long id) {
        Twit twit = this.findTwit(id);
        return likeObjService.getTwitLikes(twit);
    }

    @Override
    public List<Comment> getTwitComments(long id) {
        Twit twit = this.findTwit(id);
        return commentService.getTwitComments(twit);
    }

    @Override
    public User getTwitUser(long id) {
        return this.findTwit(id).getUser();
    }
}
