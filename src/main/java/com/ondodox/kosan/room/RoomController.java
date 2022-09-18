package com.ondodox.kosan.room;

import com.ondodox.kosan.room.dto.RoomData;
import com.ondodox.kosan.type_room.TypeRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("room")
public class RoomController {
    RoomService roomService;
    RoomRepository roomRepository;

    @Autowired
    public RoomController(RoomService service, RoomRepository roomRepository) {
        this.roomService = service;
        this.roomRepository = roomRepository;
    }

    @GetMapping
    Iterable<Room> index(){
        return roomService.getAllRoom();
    }

    @PostMapping()
    Room store(@RequestBody RoomData payload){
        TypeRoom typeRoom = new TypeRoom();
        typeRoom.setId(payload.getTypeRoomId());
        Room room = new Room();
        room.setRoomNumber(payload.getRoomNumber());
        room.setTypeRoom(typeRoom);
    return roomService.save(room);
    }

    @GetMapping("/all-id")
    List<Long> allId(){
        return roomRepository.findAllId();
    }

    @GetMapping("/{roomId}/card")
    public Map<String, Object> cardRoom(@PathVariable Long roomId){
        Map<String, Object> card = new HashMap<>();
        Room room = roomService.findOne(roomId);
        card.put("room", room);
        card.put("typeRoom", room.getTypeRoom());
        return card;
    }

    @GetMapping("/{typeRoom}/not-available")
    public List<Room> notAvailable(@PathVariable Long typeRoom){
        return roomRepository.findByTypeRoomIdAndStatus(typeRoom, false);
    }

    @GetMapping("/{typeRoom}/available")
    public List<Room> available(@PathVariable Long typeRoom){
        return roomRepository.findByTypeRoomIdAndStatus(typeRoom, true);
    }

}
