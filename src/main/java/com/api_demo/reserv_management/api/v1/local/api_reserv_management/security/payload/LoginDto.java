package com.api_demo.reserv_management.api.v1.local.api_reserv_management.security.payload;

public class LoginDto {

    private String email;
    private String password;
    private Integer verificationCode;

    public LoginDto() {}

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

    public Integer getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verificationCode=" + verificationCode +
                '}';
    }
}
