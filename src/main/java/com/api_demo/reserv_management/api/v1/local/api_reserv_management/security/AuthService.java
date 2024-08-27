package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.jwt.JwtProvider;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload.LoginDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload.SignUpDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.responses.ResponseLogin;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.userDetails.UserDetailsImpl;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.UserService;
import com.api_demo.reserv_management.api.v1.local.utils.ErrorService;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AuthService {

    private String myClassName = AuthService.class.getName();

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    public Object login(LoginDto loginDto, Integer verificationCode) {
        try {
            String pass = passwordEncoder().encode(loginDto.getPassword());
            UtilsLocal.log("Pass cifrado:" + pass);

            Object resp = userService.getByEmail(loginDto.getEmail());
            if (UtilsService.isErrorService(resp)) return resp;

            if (resp == null) {
                return new ErrorService(
                    "Usuario o contraseña incorrecta",
                    "Usuario o contraseña incorrecta",
                    myClassName,
                    400
                );
            }

            //Se intenta la autenticación
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
                )
            );

            String secretKey = userService.getSecretKeyByEmail(loginDto.getEmail()).toString();
            if (!verifyCode(secretKey, verificationCode)) {
                return new ErrorService(
                    "El codigo de autenticacion de dos factores es incorrecto",
                    "El codigo de autenticacion fallo",
                    myClassName,
                    401
                );
            }

            //Autenticamos el usuario pasandolo al contexto de la App
            SecurityContextHolder.getContext().setAuthentication(auth);

            //Geenerar el token
            String token = jwtProvider.generateToken(auth, secret, expiration);

            //Obtenemos info del usuario
            UserDetailsImpl authUserRols = (UserDetailsImpl)  auth.getPrincipal();

            ResponseLogin respLogin = new ResponseLogin(token, authUserRols.getName());
            return respLogin;
        }
        catch (Exception e) {
            return new ErrorService(
                "Usuario o contraseña incorrecta",
                e.getMessage(),
                myClassName
            );
        }
    }

    public Object Signup(SignUpDto signUpDto) {
        try {
            String pass = passwordEncoder().encode(signUpDto.getPassword());
            UtilsLocal.log("Pass cifrado:" + pass);

            Object resp = userService.signUp(signUpDto);
            if (UtilsService.isErrorService(resp)) return resp;

            //Se intenta la autenticación
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    signUpDto.getEmail(),
                    signUpDto.getPassword()
                )
            );

            //Autenticamos el usuario pasandolo al contexto de la App
            SecurityContextHolder.getContext().setAuthentication(auth);

            //String qrCodeFilePath = "QRCode_" + signUpDto.getEmail() + ".png";
            //generateQRCodeImage((String) resp, 300, 300, qrCodeFilePath);

            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                "Usuario o contraseña incorrecta",
                e.getMessage(),
                myClassName
            );
        }
    }

    public boolean verifyCode(String secretKey, Integer verificationCode) {
        GoogleAuthenticator googleAuth = new GoogleAuthenticator();
        return googleAuth.authorize(secretKey, verificationCode);
    }

    /*
        public void generateQRCodeImage(
                String text,
                Integer width,
                Integer height,
                String filePath
        ) throws Exception {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
            Path path = Paths.get(filePath);
        }
     */
}
