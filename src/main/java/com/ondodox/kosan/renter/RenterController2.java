package com.ondodox.kosan.renter;

import com.ondodox.kosan.renter.dto.DescRenterData;
import com.ondodox.kosan.room.Room;
import com.ondodox.kosan.room.RoomService;
import com.ondodox.kosan.type_room.TypeRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v2/renter")
public class RenterController2 {
    private final RenterService renterService;
    private final RoomService roomService;

    @Autowired
    public RenterController2(RenterService renterService, RoomService roomService) {
        this.renterService = renterService;
        this.roomService = roomService;
    }

    @GetMapping
    public List<Renter> renters(){
        return renterService.findAll();
    }

    @GetMapping("{renterId}")
    public Renter renter(@PathVariable Long renterId){
        return renterService.findOne(renterId);
    }

    @GetMapping("{renterId}/room")
    public Room room(@PathVariable Long renterId){
        return renter(renterId).getRoom();
    }

    @GetMapping("{renterId}/desc")
    public DescRenterData descRenterData(@PathVariable Long renterId){
        return renterService.descRenter(renter(renterId));
    }

}
