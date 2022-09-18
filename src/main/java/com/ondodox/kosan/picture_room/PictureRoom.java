package com.ondodox.kosan.picture_room;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ondodox.kosan.type_room.TypeRoom;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "picture_room")
@Data
public class PictureRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long id;
    private String pictureName;

    @ManyToOne(targetEntity = TypeRoom.class)
    @JoinColumn(name = "type_room_id")
    @JsonBackReference
    private TypeRoom typeRoom;

    public TypeRoom getTypeRoom(){
        return this.typeRoom;
    }
}
