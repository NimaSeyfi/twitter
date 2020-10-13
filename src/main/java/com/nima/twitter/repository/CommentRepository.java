package com.nima.twitter.repository;
import com.nima.twitter.domain.Comment;
import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUser(User user);
    List<Comment> findAllByTwit(Twit twit);
    List<Comment> findAllByDateBetween(Date s, Date e);
}
