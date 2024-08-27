package com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.bd1.UserEntity;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.bd1.UserRepository;
import com.api_demo.reserv_management.api.v1.local.utils.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAdapter {

    private String myClassName = UserAdapter.class.getName();

    @Autowired
    UserRepository userRepository;

    private List<UserEntity> listBd = new ArrayList<>();

    public Object getAll() {
        try {
            return userRepository.getUsersList();
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error consultando los usuarios.",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object getByEmail(String email) {
        try {
            return  userRepository.findByEmail(email);
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error consultando el usuario por correo",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object signUp(UserEntity user) {
        try {
            Object resp = userRepository.save(user);
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error guardando el usuario.",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object getSecretKeyByEmail(String email) {
        try {
            return  userRepository.getSecretKeyByEmail(email);
        }
        catch (Exception e) {
            return new ErrorService(
                "Ha ocurrido un error consultando el secret key por correo",
                e.getMessage(),
                myClassName
            );
        }
    }
}
