package com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.payloads.MeetingRoomDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.payloads.RoleDto;
import com.api_demo.reserv_management.api.v1.local.utils.ResponseLocal;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/local/api-reserv-management/meeting-rooms")
@CrossOrigin
public class MeetingRoomController {

    private String myClassName = MeetingRoomController.class.getName();

    @Autowired
    MeetingRoomService meetingRoomService;


    @GetMapping("all")
    public ResponseEntity<?> getAll(
            HttpServletRequest req
    ) {
        ResponseLocal response = new ResponseLocal("Metodo sala getAll");
        try {
            Object resp = meetingRoomService.getAll();

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
        ResponseLocal response = new ResponseLocal("Metodo room getList");

        try {
            Object resp = meetingRoomService.getList();

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
            @Valid @RequestBody MeetingRoomDto payload,
            BindingResult bindingResult,
            HttpServletRequest req
    ) {
        String action = "room-create";
        ResponseLocal response = new ResponseLocal("Metodo room create");

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
            Object resp = meetingRoomService.create(payload);

            HttpStatus httpStatus = response.validateService(
                    resp,
                    "Registro de sala ok",
                    myClassName,
                    payload.toString(),
                    req
            );
            return new ResponseEntity<>(response, httpStatus);
        }
        catch (Exception e) {
            response.setError(
                    HttpStatus.BAD_REQUEST.value(),
                    "No se pudo registrar la sala",
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
            @RequestBody MeetingRoomDto payload,
            BindingResult bindingResult,
            HttpServletRequest req
    ) {
        String action = "room-update";
        ResponseLocal response = new ResponseLocal("Metodo room update");

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
            Object resp = meetingRoomService.update(id, payload);

            HttpStatus httpStatus = response.validateService(
                    resp,
                    "Actualizacion de sala ok",
                    myClassName,
                    payload.toString(),
                    req
            );
            return new ResponseEntity<>(response, httpStatus);
        }
        catch (Exception e) {
            response.setError(
                    HttpStatus.BAD_REQUEST.value(),
                    "No se pudo actualizar la sala",
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
