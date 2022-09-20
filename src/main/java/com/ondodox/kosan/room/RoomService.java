package com.ondodox.kosan.room;

import com.ondodox.kosan.type_room.TypeRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository repository) {
        this.roomRepository = repository;
    }
    public Iterable<Room> getAllRoom(){
        try{
            return roomRepository.findAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Room save(Room room){
        return roomRepository.save(room);
    }

    public Long leftRoom(Long typeId){
        return roomRepository.countByTypeRoom_IdAndStatusFalse(typeId);
    }
    public Room findOne(Long roomId){
        return roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found!"));
    }

    public List<Room> findAllByTypeRoomId(Long typeId) {
        return roomRepository.findAllByTypeRoomId(typeId);
    }

    public Room deleteById(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        room.ifPresent(value -> roomRepository.delete(value));
        return room.orElseThrow(() -> new RuntimeException("Room not found!"));
    }

    public Integer count(Long typeId) {
        return roomRepository.countByTypeRoomId(typeId);
    }

    public Integer count(Long typeId, boolean b) {
        return roomRepository.countByTypeRoomIdAndStatus(typeId, b);
    }

    public List<Room> findAllByTypeRoomIdAndStatus(Long typeId, Boolean status) {
        return roomRepository.findByTypeRoomIdAndStatus(typeId, status);
    }

    public List<Room> findAll() {
        return (List<Room>) roomRepository.findAll();
    }


    public TypeRoom findTypeRoomByRoom(Room room) {

        return null;
    }
}
