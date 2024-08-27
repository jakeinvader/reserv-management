package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload.LoginDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload.SignUpDto;
import com.api_demo.reserv_management.api.v1.local.utils.ResponseLocal;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
//import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/local/api-reserv-management/auth")
@CrossOrigin
public class AuthController {

    private String myClassNAme = AuthController.class.getName();

    @Autowired
    AuthService authService;


    @PostMapping("login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginDto payload,
            BindingResult bindingResult,
            HttpServletRequest req
    ) {
        ResponseLocal response = new ResponseLocal("Metodo login");

        if (bindingResult.hasErrors()) {
            response.setError(
                HttpStatus.BAD_REQUEST.value(),
                "",
                "",
                bindingResult.getAllErrors(),
                myClassNAme,
                payload.toString(),
                req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Object resp = authService.login(payload, payload.getVerificationCode());


            HttpStatus httpStatus = response.validateService(
                resp,
                "Login ok",
                myClassNAme,
                payload.toString(),
                req
            );
            return new ResponseEntity<>(response, httpStatus);
        }
        catch (Exception e) {
            response.setError(
                HttpStatus.BAD_REQUEST.value(),
                "Ha ocurrido un error iniciando sesion",
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                myClassNAme,
                payload.toString(),
                req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> signUpUser(
            @Valid @RequestBody SignUpDto payload,
            BindingResult bindingResult,
            HttpServletRequest req
    ) {
        ResponseLocal response = new ResponseLocal("Metodo sign up");

        if (bindingResult.hasErrors()) {
            response.setError(
                HttpStatus.BAD_REQUEST.value(),
                "",
                "",
                bindingResult.getAllErrors(),
                myClassNAme,
                payload.toString(),
                req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Object resp = authService.Signup(payload);


            HttpStatus httpStatus = response.validateService(
                    resp,
                    "Sign Up ok",
                    myClassNAme,
                    payload.toString(),
                    req
            );
            return new ResponseEntity<>(response, httpStatus);
        }
        catch (Exception e) {
            response.setError(
                    HttpStatus.BAD_REQUEST.value(),
                    "Ha ocurrido un error rgistrando el usuario",
                    e.getMessage(),
                    UtilsLocal.emptyErrorList(),
                    myClassNAme,
                    payload.toString(),
                    req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/generate-qr-code")
    public ResponseEntity<byte[]> generateQRCodeImage(@RequestParam String text) {

        try {
            int width = 350;
            int height = 350;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            byte[] qrCodeImage = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(qrCodeImage.length);

            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
        } catch (WriterException | IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
