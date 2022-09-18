package com.ondodox.kosan.type_room.dto;

import com.ondodox.kosan.type_room.TypeRoom;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TypeRoomCard extends TypeRoom {
    Long id;
    String typeName;
    Long price;
    int roomsCount;
}
