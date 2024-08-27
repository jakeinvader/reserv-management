package com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.responses;

public class ResponseMeetingRoomDto {

    private Integer id;
    private String rommName;

    public ResponseMeetingRoomDto() {
    }

    public ResponseMeetingRoomDto(Object[] cols) {
        this.id = (Integer) cols[0];
        this.rommName = (String)  cols[1];
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRommName() {
        return rommName;
    }

    public void setRommName(String rommName) {
        this.rommName = rommName;
    }

    @Override
    public String toString() {
        return "ResponseMeetingRoomDto{" +
                "id=" + id +
                ", rommName='" + rommName + '\'' +
                '}';
    }
}
