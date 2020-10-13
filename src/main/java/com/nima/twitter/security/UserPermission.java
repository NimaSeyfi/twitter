package com.nima.twitter.security;

public enum UserPermission {
    TWEET_READ("tweet:read"),
    TWEET_WRITE("tweet:write"),
    LIKE_READ("like:read"),
    LIKE_WRITE("like:write"),
    COMMENT_READ("comment:read"),
    COMMENT_WRITE("comment:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");


    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
