package com.tommy.authentication.config.security;

import com.tommy.authentication.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class UserDetailImpl implements UserDetails {

    private String email;
    private String passowrd;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailImpl builder(User user){
        String role = user.getRoles().iterator().next().getRoleType().name();
        String[] roles = new String[]{role};

        return new UserDetailImpl(user.getEmail(),user.getPassword(), AuthorityUtils.createAuthorityList(roles));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passowrd;
    }

    @Override
    public String getUsername() {
        return email;
    }

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
