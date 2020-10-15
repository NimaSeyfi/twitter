package com.nima.twitter.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;
import static com.nima.twitter.security.UserPermission.*;


public enum UserRole {
    CLIENT(Sets.newHashSet(LIKE_READ,
            LIKE_WRITE,
            COMMENT_READ,
            COMMENT_WRITE,
            TWIT_READ,
            TWIT_WRITE,
            USER_READ)),
    ADMIN(Sets.newHashSet(LIKE_READ,
            LIKE_WRITE,
            COMMENT_READ,
            COMMENT_WRITE,
            TWIT_READ,
            TWIT_WRITE
            ,
            USER_READ,
            USER_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
