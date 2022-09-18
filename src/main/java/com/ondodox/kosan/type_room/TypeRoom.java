package com.ondodox.kosan.type_room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ondodox.kosan.picture_room.PictureRoom;
import com.ondodox.kosan.room.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "type_room")
@NoArgsConstructor
@AllArgsConstructor
public class TypeRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Long id;

    private String typeName;

    private Long price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "typeRoom")
    @JsonIgnore
    private List<Room> rooms;


    @OneToMany(mappedBy = "typeRoom")
    @JsonIgnore
    private List<PictureRoom> pictureRooms;

    public TypeRoom(Long id) {
        this.id = id;
    }

}
