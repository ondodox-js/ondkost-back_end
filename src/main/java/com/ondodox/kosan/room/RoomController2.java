package com.ondodox.kosan.room;

import com.ondodox.kosan.renter.Renter;
import com.ondodox.kosan.renter.RenterService;
import com.ondodox.kosan.renter.dto.RenterData;
import com.ondodox.kosan.user.User;
import com.ondodox.kosan.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("v2/room")
public class RoomController2 {

    private final RoomService roomService;
    private final RenterService renterService;
    private final UserService userService;

    @Autowired
    public RoomController2(RoomService roomService, RenterService renterService, UserService userService) {
        this.roomService = roomService;
        this.renterService = renterService;
        this.userService = userService;
    }

    @GetMapping
    public List<Room> rooms(){
        return roomService.findAll();
    }

    @GetMapping("{roomId}")
    public Room room(@PathVariable Long roomId){
        return roomService.findOne(roomId);
    }

    @DeleteMapping("{roomId}")
    public Room roomDestroy(@PathVariable Long roomId){
        return roomService.deleteById(roomId);
    }

    @PostMapping("{roomId}/renter")
    public Renter renter(@PathVariable Long roomId, @RequestBody RenterData renterData){
        Room room = roomService.findOne(roomId);
        User user = new User(renterData.getName());

        Renter renter = new Renter();
        renter.setRoom(room);
        renter.setPeriod(renterData.getPeriod());
        renter.setUser(user);
        renter.setCreatedAt(LocalDateTime.now());

        room.setStatus(true);
        userService.save(user);
        roomService.save(room);

        return renterService.save(renter);
    }
}
