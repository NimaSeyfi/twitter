package com.nima.twitter.service.serviceImp;

import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import com.nima.twitter.exception.Exception404;
import com.nima.twitter.repository.CommentRepository;
import com.nima.twitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    @Autowired
    public CommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public Comment create(User user, Twit twit, String text, Date date) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTwit(twit);
        comment.setText(text);
        comment.setDate(date);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(long id, User user, Twit twit, String text, Date date) throws Exception404 {
        Comment comment = this.findComment(id);
        comment.setUser(user);
        comment.setTwit(twit);
        comment.setText(text);
        comment.setDate(date);
        return commentRepository.save(comment);
    }

    @Override
    public void delete(long id) throws Exception404 {
        Comment comment = this.findComment(id);
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getTwitComments(Twit twit) {
        return commentRepository.findAllByTwit(twit);
    }

    @Override
    public List<Comment> getUserComments(User user) {
        return commentRepository.findAllByUser(user);
    }

    @Override
    public Comment findComment(long id) throws Exception404 {
        return commentRepository.findById(id)
                .orElseThrow(
                        ()->new Exception404(String.format("Comment not found with id : %d",id))
                );
    }

    @Override
    public List<Comment> findCommentsBetweenDates(Date s, Date e) {
        return commentRepository.findAllByDateBetween(s, e);
    }
}
