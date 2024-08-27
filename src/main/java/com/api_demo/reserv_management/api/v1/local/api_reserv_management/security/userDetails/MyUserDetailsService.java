package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.userDetails;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.RoleService;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.bd1.RolEntity;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.UserService;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.bd1.UserEntity;
import com.api_demo.reserv_management.api.v1.local.utils.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Object resp = userService.getByEmail(email);

        if (resp == null) {
            throw  new UsernameNotFoundException("Usuario no encontrado");
        }

        UserEntity user = (UserEntity) resp;

        return new UserDetailsImpl(user);
    }
}
