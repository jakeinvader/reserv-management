package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.responses;

public class ResponseLogin {

    private String token;
    private String name;

    public ResponseLogin() {
    }

    public ResponseLogin(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
