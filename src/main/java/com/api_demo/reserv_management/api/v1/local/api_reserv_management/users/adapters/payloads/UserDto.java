package com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;
    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "Debe ingresar un correo valido")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String password;

    public UserDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
