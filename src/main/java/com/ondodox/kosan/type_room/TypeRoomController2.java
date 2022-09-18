package com.ondodox.kosan.type_room;

import com.ondodox.kosan.renter.Renter;
import com.ondodox.kosan.renter.RenterService;
import com.ondodox.kosan.room.Room;
import com.ondodox.kosan.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v2/type-room")
public class TypeRoomController2 {
    private final TypeRoomService typeRoomService;
    private final RoomService roomService;
    private final RenterService renterService;

    @Autowired
    public TypeRoomController2(TypeRoomService typeRoomService, RoomService roomService, RenterService renterService) {
        this.typeRoomService = typeRoomService;
        this.roomService = roomService;
        this.renterService = renterService;
    }

    @GetMapping
    public List<TypeRoom> typeRooms(){
        return (List<TypeRoom>) typeRoomService.findAll();
    }

    @GetMapping("id")
    public List<Map<String, Long>> typeRoomId(){
        return typeRoomService.findAllId();
    }

    @PostMapping
    public TypeRoom typeRoom(@RequestBody TypeRoom typeRoom){
        return typeRoomService.save(typeRoom);
    }

    @GetMapping("{typeId}")
    public TypeRoom typeRoom(@PathVariable Long typeId){
        return typeRoomService.findOne(typeId);
    }

    @PutMapping("{typeId}")
    public TypeRoom typeRoom(@PathVariable Long typeId,@RequestBody TypeRoom typeRoom){
        typeRoom.setId(typeId);
        return typeRoomService.save(typeRoom);
    }

    @DeleteMapping("{typeId}")
    public TypeRoom typeRoomDelete(@PathVariable Long typeId){
        return typeRoomService.deleteById(typeId);
    }

    @GetMapping("{typeId}/room")
    public List<Room> rooms(@PathVariable Long typeId){
        return roomService.findAllByTypeRoomId(typeId);
    }

    @GetMapping("{typeId}/room-stock")
    public Map<String, Integer> roomStock(@PathVariable Long typeId){
        Map<String, Integer> stockRoom = new HashMap<>();
        stockRoom.put("typeRoomId", Math.toIntExact(typeId));
        stockRoom.put("amount", roomService.count(typeId));
        stockRoom.put("left", roomService.count(typeId, false));

        return stockRoom;
    }

    @PostMapping("{typeId}/room")
    public Room room(@PathVariable Long typeId,@RequestBody Room room){
        room.setTypeRoom(new TypeRoom(typeId));
        return roomService.save(room);
    }

    @GetMapping("{typeId}/room/{roomId}")
    public Room room(@PathVariable Long typeId, @PathVariable Long roomId){
        return roomService.findOne(roomId);
    }

    @PutMapping("{typeId}/room/{roomId}")
    public Room room(@PathVariable Long typeId, @PathVariable Long roomId,@RequestBody Room room){
        room.setId(roomId);
        room.setTypeRoom(new TypeRoom(typeId));
        return roomService.save(room);
    }

    @DeleteMapping("{typeId}/room/{roomId}")
    public Room roomDestroy(@PathVariable Long typeId, @PathVariable Long roomId){
        return roomService.deleteById(roomId);
    }

    @GetMapping("{typeId}/room-status/{status}")
    public List<Room> rooms(@PathVariable Long typeId, @PathVariable Boolean status){
        return roomService.findAllByTypeRoomIdAndStatus(typeId, status);
    }

    @GetMapping("{typeId}/renter")
    public List<Renter> renters(@PathVariable Long typeId){
        return renterService.findAllByTypeRoomIdAndStatus(typeId, true);
    }
}
