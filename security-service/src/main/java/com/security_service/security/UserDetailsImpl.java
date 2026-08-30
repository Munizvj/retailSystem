package com.security_service.security;

import com.security_service.model.User.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (user.getPosition() != null && user.getPosition().getPermissions() != null) {
            user.getPosition().getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getPermission().name()))
            );

            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getPosition().getRole().name()));
        }

        if (user.getExtraPermission() != null) {
            user.getExtraPermission().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getPermission().name()))
            );
        }

        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
