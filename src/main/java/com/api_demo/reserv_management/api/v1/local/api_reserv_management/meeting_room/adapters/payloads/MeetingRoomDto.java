package com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.payloads;

import jakarta.persistence.Column;

public class MeetingRoomDto {

    public String roomName;
    public String capacity;
    public String availableEquipment;
    public String availableTime;

    public MeetingRoomDto() {
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getAvailableEquipment() {
        return availableEquipment;
    }

    public void setAvailableEquipment(String availableEquipment) {
        this.availableEquipment = availableEquipment;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    @Override
    public String toString() {
        return "MeetingRoomDto{" +
                "roomName='" + roomName + '\'' +
                ", capacity='" + capacity + '\'' +
                ", availableEquipment='" + availableEquipment + '\'' +
                ", availableTime='" + availableTime + '\'' +
                '}';
    }
}
