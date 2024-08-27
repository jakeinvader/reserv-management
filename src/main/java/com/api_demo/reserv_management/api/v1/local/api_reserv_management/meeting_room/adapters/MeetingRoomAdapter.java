package com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.bd1.MeetingRoomEntity;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.bd1.MeetingRoomRepository;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.bd1.RolEntity;
import com.api_demo.reserv_management.api.v1.local.utils.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingRoomAdapter {

    private String myClassName = MeetingRoomAdapter.class.getName();

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    public Object getAll() {
        try {
            Object resp = meetingRoomRepository.findAll();
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error consultando las salas.",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object getList() {
        try {
            String sql = "select id, room_name from meeting_rooms where active = true order by id asc";

            Object resp = meetingRoomRepository.findDataBySql(sql);
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error obteniendo la lista de salas",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object createOrEdit(MeetingRoomEntity meetingRoomEntity) {
        try {
            Object resp = meetingRoomRepository.save(meetingRoomEntity);
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error guardando la sala",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object getRoomById(String id) {
        try {
            Integer idInteger = Integer.parseInt(id);
            return  meetingRoomRepository.getRoleById(idInteger);
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error consultando el rol por id",
                    e.getMessage(),
                    myClassName
            );
        }
    }

}
