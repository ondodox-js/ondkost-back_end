package com.ondodox.kosan.type_room;

import com.ondodox.kosan.picture_room.PictureRoom;
import com.ondodox.kosan.picture_room.PictureRoomRepository;
import com.ondodox.kosan.room.Room;
import com.ondodox.kosan.type_room.dto.TypeRoomCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class TypeRoomService {
    private final TypeRoomRepository typeRoomRepository;
    private final PictureRoomRepository pictureRoomRepository;

    @Autowired
    public TypeRoomService(TypeRoomRepository typeRoomRepository, PictureRoomRepository pictureRoomRepository) {
        this.typeRoomRepository = typeRoomRepository;
        this.pictureRoomRepository = pictureRoomRepository;
    }

    TypeRoom createType(TypeRoom room){
        return typeRoomRepository.save(room);
    }

    void insertPicture(PictureRoom pictureRoom){
        pictureRoomRepository.save(pictureRoom);
    }

    Iterable<TypeRoom> findAll(){
        return typeRoomRepository.findAll();
    }

    TypeRoom findOne(Long id){
        return typeRoomRepository.findById(id).orElseThrow(() -> new RuntimeException("Typeroom not found!"));
    }

    public TypeRoom save(TypeRoom typeRoom) {
        return typeRoomRepository.save(typeRoom);
    }

    public TypeRoom deleteById(Long typeId) {
        TypeRoom typeRoom = findOne(typeId);
        typeRoomRepository.delete(typeRoom);
        return typeRoom;
    }

    public List<Map<String, Long>> findAllId() {
        List<Map<String, Long>> data = new LinkedList<>();
        typeRoomRepository.findAll().forEach(typeRoom -> {
            Map<String, Long> typeRoomsId = new HashMap<>();
            typeRoomsId.put("typeRoomId", typeRoom.getId());
            data.add(typeRoomsId);
        });

        return data;
    }
}
