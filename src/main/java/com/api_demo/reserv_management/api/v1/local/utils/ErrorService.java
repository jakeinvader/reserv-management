package com.api_demo.reserv_management.api.v1.local.utils;

public class ErrorService {

    public String message;
    public String description;
    public String classPath;
    public Integer code;

    public ErrorService(String message, String description, String classPath) {
        if (message.equals("No hay registro")) {
            this.message = "No se encontró el registro";
            this.description = "No se encontró el registro con ID: " + description;
            this.code = 404;
        }
        else  {
            this.message = message;
            this.description = description;
            this.code = 500;
        }
        this.classPath = classPath;
    }

    public ErrorService(String message, String description, String classPath, Integer code) {
        this.message = message;
        this.description = description;
        this.classPath = classPath;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
