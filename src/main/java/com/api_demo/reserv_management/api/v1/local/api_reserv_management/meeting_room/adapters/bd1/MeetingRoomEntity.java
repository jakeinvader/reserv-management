package com.api_demo.reserv_management.api.v1.local.api_reserv_management.meeting_room.adapters.bd1;

import jakarta.persistence.*;

@Entity
@Table(name = "meeting_rooms")
public class MeetingRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "room_name")
    public String roomName;
    public String capacity;
    @Column(name = "available_equipment")
    public String availableEquipment;
    @Column(name = "available_time")
    public String availableTime;

    public MeetingRoomEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "MeetingRoomEntity{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", capacity='" + capacity + '\'' +
                ", availableEquipment='" + availableEquipment + '\'' +
                ", availableTime='" + availableTime + '\'' +
                '}';
    }
}
