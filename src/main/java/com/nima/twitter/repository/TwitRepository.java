package com.nima.twitter.repository;

import com.nima.twitter.domain.Twit;
import com.nima.twitter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TwitRepository extends JpaRepository<Twit, Long> {
    List<Twit> findAllByUser(User user);
}
