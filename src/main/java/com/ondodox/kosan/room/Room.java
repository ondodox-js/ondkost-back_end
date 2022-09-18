package com.ondodox.kosan.room;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ondodox.kosan.renter.Renter;
import com.ondodox.kosan.type_room.TypeRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
public class Room{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Long id;

    private Long roomNumber;

    @Column(columnDefinition = "boolean default false")
    private boolean status;

    @ManyToOne(targetEntity = TypeRoom.class)
    @JoinColumn(name = "type_room_id")
    @JsonIgnore
    private TypeRoom typeRoom;

    public TypeRoom getTypeRoom(){
        return this.typeRoom;
    }

    public Room(Long id) {
        this.id = id;
    }
}
