package com.example.finalproject.service.security;

import com.example.finalproject.entity.User;
import com.example.finalproject.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final User user;
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> mapRolesToGrantedAuthorities(List<Role> roles)
    {
        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(role-> new SimpleGrantedAuthority(role.getName())).
                toList();

//        Collection<? extends GrantedAuthority> authorities = roles.stream()
//                .map(role-> new GrantedAuthority() {
//                    @Override
//                    public String getAuthority() {
//                        return role.getName();
//                    }
//                } ).toList();
//
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapRolesToGrantedAuthorities(user.getRoles());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail(){return user.getEmail();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
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

