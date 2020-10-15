package com.nima.twitter.domain;

import org.springframework.web.bind.annotation.DeleteMapping;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    @Column
    private String text;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Twit.class)
    private Twit twit;

    @Column
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Twit getTwit() {
        return twit;
    }

    public void setTwit(Twit twit) {
        this.twit = twit;
    }
}
