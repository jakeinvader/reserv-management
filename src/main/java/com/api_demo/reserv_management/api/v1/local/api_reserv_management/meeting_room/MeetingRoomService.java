package com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.MeetingRoomAdapter;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.bd1.MeetingRoomEntity;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.payloads.MeetingRoomDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.responses.ResponseMeetingRoomDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.bd1.RolEntity;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.payloads.RoleDto;
import com.api_demo.reserv_management.api.v1.local.utils.ErrorService;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MeetingRoomService {

    private String myClassName = MeetingRoomService.class.getName();

    @Autowired
    MeetingRoomAdapter meetingRoomAdapter;

    public Object getAll() {
        try {
            Object resp = meetingRoomAdapter.getAll();
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error consultando las salas",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object getList() {
        try {
            ArrayList<Object> resp = (ArrayList<Object>)  meetingRoomAdapter.getList();
            ArrayList<ResponseMeetingRoomDto> listData = new ArrayList<>();
            resp.stream().map(item -> new ResponseMeetingRoomDto((Object[]) item)).forEachOrdered(resData -> {
                listData.add(resData);
            });
            return listData;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error obteniendo la lista de roles",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object create(MeetingRoomDto meetingRoomDto) {
        try {
            MeetingRoomEntity meetingRoomEntity = new MeetingRoomEntity();
            meetingRoomEntity.setRoomName(meetingRoomDto.getRoomName());
            meetingRoomEntity.setCapacity(meetingRoomDto.getCapacity());
            meetingRoomEntity.setAvailableEquipment(meetingRoomDto.getAvailableEquipment());
            meetingRoomEntity.setAvailableTime(meetingRoomDto.getAvailableTime());

            Object resp = meetingRoomAdapter.createOrEdit(meetingRoomEntity);
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error creando el rol",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object update(Integer id, MeetingRoomDto meetingRoomDto) {
        try {
            Object roomExist = meetingRoomAdapter.getRoomById(String.valueOf(id));
            if (UtilsService.isErrorService(roomExist)) return roomExist;

            if (roomExist == null) {
                return new ErrorService(
                        "La sala que intenta actualizar no existe",
                        "La sala con ID = "+id+" no existe",
                        myClassName
                );
            }

            MeetingRoomEntity meetingRoomEntity = new MeetingRoomEntity();
            meetingRoomEntity.setId(Long.valueOf(id));
            meetingRoomEntity.setRoomName(meetingRoomDto.getRoomName());
            meetingRoomEntity.setCapacity(meetingRoomDto.getCapacity());
            meetingRoomEntity.setAvailableEquipment(meetingRoomDto.getAvailableEquipment());
            meetingRoomEntity.setAvailableTime(meetingRoomDto.getAvailableTime());

            Object resp = meetingRoomAdapter.createOrEdit(meetingRoomEntity);
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error actualizando la sala",
                    e.getMessage(),
                    myClassName
            );
        }
    }
}
