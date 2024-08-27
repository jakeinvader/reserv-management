package com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters;

import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.bd1.RolEntity;
import com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.bd1.RoleRepository;
import com.api_demo.reserv_management.api.v1.local.utils.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleAdapter {

    private String myClassName = RoleAdapter.class.getName();

    @Autowired
    RoleRepository roleRepository;

    public Object getAll() {
        try {
            Object resp = roleRepository.findAll();
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error consultando los roles.",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object getList() {
        try {
            String sql = "select r.id, r.role from rols r order by r.id asc;";

            Object resp = roleRepository.findDataBySql(sql);
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error obteniendo la lista de roles",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object createOrEdit(RolEntity rolEntity) {
        try {
            Object resp = roleRepository.save(rolEntity);
            return resp;
        }
        catch (Exception e) {
            return new ErrorService(
                    "Ha ocurrido un error guardando el rol",
                    e.getMessage(),
                    myClassName
            );
        }
    }

    public Object getRoleById(String id) {
        try {
            Integer idInteger = Integer.parseInt(id);
            return  roleRepository.getRoleById(idInteger);
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
