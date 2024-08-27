package com.api_demo.reserv_management.api.v1.local.api_reserv_management.users;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.RoleService;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload.SignUpDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.UserAdapter;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.bd1.UserEntity;
import com.api_demo.reserv_management.api.v1.local.utils.ErrorService;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private String myClassName = UserService.class.getName();

    @Autowired
    UserAdapter userAdapter;

    @Autowired
    RoleService roleService;


    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    public Object getAll() {
        try {
            Object resp = userAdapter.getAll();
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                "Ha ocurrido un error consultando los usuarios",
                e.getMessage(),
                myClassName
            );
        }
    }

    public Object getByEmail(String email) {
        try {
            Object resp = userAdapter.getByEmail(email);
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                "Ha ocurrido un error consultando el usuario por email",
                e.getMessage(),
                myClassName
            );
        }
    }

    public Object getSecretKeyByEmail(String email) {
        try {
            Object resp = userAdapter.getSecretKeyByEmail(email);
            return  resp;
        }
        catch (Exception e) {
            return new ErrorService(
                "Ha ocurrido un error consultando el secret key por correo",
                e.getMessage(),
                myClassName
            );
        }
    }

    public Object signUp(SignUpDto signUpDto) {
        try {
            Object respValEmail = userAdapter.getByEmail(signUpDto.getEmail());
            if (UtilsService.isErrorService(respValEmail)) return  respValEmail;

            UserEntity userExits = (UserEntity)  respValEmail;
            if (userExits != null) {
                return  new ErrorService(
                    "El usuario ya se encuentra registrado",
                    "El correo ya se encuentra registrado",
                    myClassName,
                    400
                );
            }

            UserEntity user = new UserEntity();
            user.setName(signUpDto.getName());
            user.setEmail(signUpDto.getEmail());
            String password = passwordEncoder().encode(signUpDto.getPassword());
            user.setPassword(password);
            user.setRole(1);
            user.setSecretKey(generateSecretKey());

            String qrCodeUrl = getQrCode(user.getSecretKey(), signUpDto.getEmail());

            Object resp = userAdapter.signUp(user);
            return qrCodeUrl;
        }
        catch (Exception e) {
            return new ErrorService(
                "Ha ocurrido un error registrando el usuario",
                e.getMessage(),
                myClassName
            );
        }
    }

    public String generateSecretKey() {
        GoogleAuthenticator googleAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey googleKey = googleAuth.createCredentials();
        return googleKey.getKey();
    }

    public String getQrCode(String secretKey, String email) {
        String issuer = "ReservManagement";
        return "otpauth://totp/" + issuer + ":" + email + "?secret=" + secretKey + "&issuer=" + issuer;
    }
}
