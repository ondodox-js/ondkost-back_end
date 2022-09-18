package com.ondodox.kosan.room.dto;

import lombok.Data;

@Data
public class RoomData {
    Long id;
    Long roomNumber;
    Long typeRoomId;
    boolean status;
}
