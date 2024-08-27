package com.api_demo.reserv_management.api.v1.local.api_reserv_management.users.adapters.payloads;

import jakarta.validation.constraints.NotBlank;

public class HeadersDto {

    @NotBlank(message = "El token temporal es requerido...")
    private String tokenTmp;

    public HeadersDto(String tokenTmp) { this.tokenTmp = tokenTmp; }

    public String getTokenTmp() { return tokenTmp; }

    public void setTokenTmp(String tokenTmp) {
        this.tokenTmp = tokenTmp;
    }

    @Override
    public String toString() {
        return "HeadersDto{" +
                "tokenTmp='" + tokenTmp + '\'' +
                '}';
    }
}
