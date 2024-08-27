package com.api_demo.reserv_management.api.v1.local.api_reserv_management.users;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload.SignUpDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.payloads.HeadersDto;
import com.api_demo.reserv_management.api.v1.local.utils.ResponseLocal;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/local/api-reserv-management/users")
@CrossOrigin
public class UserController {

    private String myClassName = UserController.class.getName();

    @Autowired
    UserService userService;


    @GetMapping("all")
    public ResponseEntity<?> getAll(
            HttpServletRequest req
    ) {
        ResponseLocal response = new ResponseLocal("Metodo usuario all");
        try {
            Object resp = userService.getAll();

            HttpStatus httpStatus = response.validateService(
                resp,
                "Consulta ok",
                myClassName,
                "",
                req
            );
            return new ResponseEntity<>(response, httpStatus);
        }
        catch (Exception e) {
            response.setError(
                HttpStatus.BAD_REQUEST.value(),
                "No se pudo obtener la lista de usuarios",
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                myClassName,
                "",
                req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("create")
    public ResponseEntity<?> create(
            @Valid @RequestBody SignUpDto payload,
            BindingResult bindingResult,
            HttpServletRequest req
    ) {
        ResponseLocal response = new ResponseLocal("Metodo usuario create");

        // Implemento validacion manual de un Dto:
        Validator validator = UtilsLocal.getValidatorDto();
        String tokenTmp = req.getHeader("tokenTmp");
        HeadersDto headersDto = new HeadersDto(tokenTmp);

        Set<ConstraintViolation<HeadersDto>> errorsResp = validator.validate(headersDto);
        if (!errorsResp.isEmpty() && errorsResp.size() != 0) {
            List<String> errorsList = new ArrayList<>();
            errorsResp.forEach( er -> errorsList.add(er.getMessage()));
            response.setError(
                HttpStatus.BAD_REQUEST.value(),
                UtilsLocal.getErrorMessagesList(errorsList),
                "",
                UtilsLocal.emptyErrorList(),
                this.myClassName,
                headersDto.toString(),
                req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            response.setError(
                HttpStatus.BAD_REQUEST.value(),
                "",
                "",
                bindingResult.getAllErrors(),
                myClassName,
                payload.toString(),
                req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Object resp = userService.signUp(payload);

            HttpStatus httpStatus = response.validateService(
                resp,
                "Registro de usuario ok",
                myClassName,
                payload.toString(),
                req
            );
            return new ResponseEntity<>(response, httpStatus);
        }
        catch (Exception e) {
            response.setError(
                HttpStatus.BAD_REQUEST.value(),
                "No se pudo registrar el usuario",
                e.getMessage(),
                UtilsLocal.emptyErrorList(),
                myClassName,
                payload.toString(),
                req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
