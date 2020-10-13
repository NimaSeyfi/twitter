package com.nima.twitter.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Twit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @Column
    private String content;

    @Column
    private Date pubDate;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
}
