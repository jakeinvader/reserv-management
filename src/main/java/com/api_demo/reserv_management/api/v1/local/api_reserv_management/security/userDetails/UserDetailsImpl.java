package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.userDetails;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.bd1.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final UserEntity user;

    public UserDetailsImpl(UserEntity user) { this.user = user; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*String roleName = getRole();
        if (roleName != null) {
            return Collections.singletonList(new SimpleGrantedAuthority(roleName));
        } else {
            return Collections.emptyList();
        }*/
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getName() { return user.getName(); }
    /*public String getRole() {
        switch (user.getRole()) {
            case 1:
                return "ROLE_USER";
            case 2:
                return "ROLE_ADMIN";
            default:
                return "ROLE_UNKNOW";
        }
    }*/

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true;}

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {return true; }
}
