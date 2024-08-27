package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload;

public class SignUpDto {

    private String name;
    private String email;
    private String password;
    private String secretKey;

    public SignUpDto() {
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "SignUpDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
