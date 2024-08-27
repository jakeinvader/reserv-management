package com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.RoleAdapter;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.bd1.RolEntity;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.payloads.RoleDto;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.responses.ResponseRoleListDto;
import com.api_demo.reserv_management.api.v1.local.utils.ErrorService;
import com.api_demo.reserv_management.api.v1.local.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoleService {

    private String myClassName = RoleService.class.getName();

    @Autowired
    RoleAdapter roleAdapter;

    public Object getAll() {
        try {
            Object resp = roleAdapter.getAll();
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                "Ha ocurrido un error consultando los roles",
                e.getMessage(),
                myClassName
            );
        }
    }

    public Object getList() {
        try {
            /*
            ArrayList<Object> resp = (ArrayList<Object>) roleAdapter.getList();
            ArrayList<ResponseRoleListDto> listData = new ArrayList<>();
            resp.stream().map(item -> new ResponseRoleListDto((Object[]) item)).forEachOrdered(resData -> {
                listData.add(resData);
            });
            return listData;
             */
            Object result = roleAdapter.getList();
            if (result instanceof ArrayList) {
                ArrayList<Object> resp = (ArrayList<Object>) result;
                ArrayList<ResponseRoleListDto> listData = new ArrayList<>();
                resp.stream()
                        .map(item -> new ResponseRoleListDto((Object[]) item))
                        .forEachOrdered(listData::add);
                return listData;
            } else {
                return new ErrorService(
                        "El resultado no es una lista válida",
                        "El objeto devuelto no es un ArrayList",
                        myClassName
                );
            }
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error obteniendo la lista de roles",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object create(RoleDto roleDto) {
        try {
            RolEntity rolEntity = new RolEntity();
            rolEntity.setRole(roleDto.getNRole());

            Object resp = roleAdapter.createOrEdit(rolEntity);
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

    public Object update(Integer id, RoleDto roleDto) {
        try {
            Object roleExist = roleAdapter.getRoleById(String.valueOf(id));
            if (UtilsService.isErrorService(roleExist)) return roleExist;

            if (roleExist == null) {
                return new ErrorService(
                        "El rol que intenta actualizar no existe",
                        "El rol con ID = "+id+" no existe",
                        myClassName
                );
            }

            RolEntity rolEntity = new RolEntity();
            rolEntity.setId(Long.valueOf(id));
            rolEntity.setRole(roleDto.getNRole());

            Object resp = roleAdapter.createOrEdit(rolEntity);
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error actualizando el rol",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object getRoleByIDl(String id) {
        try {
            Object resp = roleAdapter.getRoleById(id);
            return  resp;
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
