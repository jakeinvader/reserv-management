package com.api_demo.reserv_management.api.v1.local.api_reserv_management.roles.adapters.responses;

public class ResponseRoleListDto {

    private Integer id;
    private String role;

    public ResponseRoleListDto() {
    }

    public ResponseRoleListDto( Object[] cols) {
        this.id = (Integer)  cols[0];
        this.role = (String) cols[1] != null? (String) cols[1] : "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "ResponseRoleListDto{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
