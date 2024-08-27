package com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RoleDto {

    @NotBlank(message = "El nombre del rol no puede estar vacio")
    @Pattern(regexp = "^[A-Z]+$", message = "El role solo debe contener letras mayúsculas sin caracteres especiales")
    private String role;

    public RoleDto() {
    }

    public String getNRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "role='" + role + '\'' +
                '}';
    }
}
