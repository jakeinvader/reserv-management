package com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.payloads.RoleDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload.SignUpDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.payloads.HeadersDto;
import com.api_demo.reserv_management.api.v1.local.utils.ResponseLocal;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/local/api-reserv-management/roles")
@CrossOrigin
public class RoleController {

    private String myClassName = RoleController.class.getName();

    @Autowired
    RoleService roleService;

    @GetMapping("all")
    public ResponseEntity<?> getAll(
            HttpServletRequest req
    ) {
        ResponseLocal response = new ResponseLocal("Metodo rol getAll");
        try {
            Object resp = roleService.getAll();

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
                    "No se pudo obtener la lista de roles",
                    e.getMessage(),
                    UtilsLocal.emptyErrorList(),
                    myClassName,
                    "",
                    req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("list")
    public ResponseEntity<?> getList(
            HttpServletRequest req
    ) {
        String action = "getList";
        ResponseLocal response = new ResponseLocal("Metodo rol getList");

        try {
            Object resp = roleService.getList();

            HttpStatus httpStatus = response.validateService(resp,
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
                    "No se pudo obtener la lista de roles",
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
            @Valid @RequestBody RoleDto payload,
            BindingResult bindingResult,
            HttpServletRequest req
    ) {
        String action = "role-create";
        ResponseLocal response = new ResponseLocal("Metodo rol create");

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
            Object resp = roleService.create(payload);

            HttpStatus httpStatus = response.validateService(
                    resp,
                    "Registro de rol ok",
                    myClassName,
                    payload.toString(),
                    req
            );
            return new ResponseEntity<>(response, httpStatus);
        }
        catch (Exception e) {
            response.setError(
                    HttpStatus.BAD_REQUEST.value(),
                    "No se pudo registrar el rol",
                    e.getMessage(),
                    UtilsLocal.emptyErrorList(),
                    myClassName,
                    payload.toString(),
                    req
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("update/id")
    public ResponseEntity<?> update(
            @Valid @RequestParam Integer id,
            @RequestBody RoleDto payload,
            BindingResult bindingResult,
            HttpServletRequest req
    ) {
        String action = "role-update";
        ResponseLocal response = new ResponseLocal("Metodo rol update");

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
            Object resp = roleService.update(id, payload);

            HttpStatus httpStatus = response.validateService(
                    resp,
                    "Actualizacion de rol ok",
                    myClassName,
                    payload.toString(),
                    req
            );
            return new ResponseEntity<>(response, httpStatus);
        }
        catch (Exception e) {
            response.setError(
                    HttpStatus.BAD_REQUEST.value(),
                    "No se pudo actualizar el rol",
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
